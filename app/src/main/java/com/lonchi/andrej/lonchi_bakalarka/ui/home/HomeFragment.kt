package com.lonchi.andrej.lonchi_bakalarka.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.logic.util.checkCameraPermissions
import com.lonchi.andrej.lonchi_bakalarka.logic.util.checkWriteExtStoragePermissions
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel>() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var camera: Camera
    private val PERMISSION_REQUEST_CODE = 1

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        camera = Camera.Builder()
            .resetToCorrectOrientation(true)//1
            .setTakePhotoRequestCode(Camera.REQUEST_TAKE_PHOTO)//2
            .setDirectory("pics")//3
            .setName("delicious_${System.currentTimeMillis()}")//4
            .setImageFormat(Camera.IMAGE_JPEG)//5
            .setCompression(75)//6
            .build(this)

        btnPicture.setOnClickListener {
            takePicture()
        }
    }

    override fun bindViewModel() = Unit

    override fun onDestroy() {
        super.onDestroy()
        camera.deleteImage()
    }

    fun takePicture() {
        Timber.d("takePicture:")
        camera.takePicture()
        return
        if(
            !requireContext().checkWriteExtStoragePermissions() ||
            !requireContext().checkCameraPermissions()
        ) {
            Timber.d("takePicture: in if")
            requestPermissions()
        } else {
            // else all permissions granted, go ahead and take a picture using camera
            try {
                Timber.d("takePicture: in try")
                camera.takePicture()
            } catch (e: Exception) {
                Timber.d("takePicture: catch")
                // Show a toast for exception
                Toast.makeText(
                    requireContext(),
                    "R.string.error_taking_picture",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun requestPermissions(){
        Timber.d("requestPermissions:")
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(requireContext(), "volake hgovno", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ),
                PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    try {
                        Timber.d("onRequestPermissionsResult: in try")
                        camera.takePicture()
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "R.string.error_taking_picture",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            camera.cameraBitmap?.let {
                img.setImageBitmap(it)
                detectDeliciousFoodOnCloud(it)
            } ?: run {
                Toast.makeText(requireContext(), "Bitmap is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun detectDeliciousFoodOnCloud(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val options = FirebaseVisionCloudImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
        val labeler = FirebaseVision.getInstance().getCloudImageLabeler(options)

        labeler.processImage(image)
            .addOnSuccessListener {
                Timber.d("detectDeliciousFoodOnCloud: result = ${it.size}")
                it.forEach {
                    Timber.d("detectDeliciousFoodOnCloud item: ${it.text} | ${it.entityId} | ${it.confidence}")
                }
            }
            .addOnFailureListener {
                Timber.e("detectDeliciousFoodOnCloud: error $it")
            }
    }
}