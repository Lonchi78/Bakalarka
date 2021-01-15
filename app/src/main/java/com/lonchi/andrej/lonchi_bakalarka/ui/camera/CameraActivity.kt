package com.lonchi.andrej.lonchi_bakalarka.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.*
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.logic.util.*
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet.FoundIngredientsBottomSheet
import kotlinx.android.synthetic.main.activity_camera.*
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CameraActivity : BaseActivity<CameraViewModel>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, CameraActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }

        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override val layoutId: Int? = R.layout.activity_camera
    override val vmClassToken: Class<CameraViewModel> = CameraViewModel::class.java

    private var ingredientsBottomSheet: FoundIngredientsBottomSheet? = null

    private lateinit var cameraExecutor: ExecutorService

    private val CAMERA_SELECTOR = CameraSelector.DEFAULT_BACK_CAMERA
    private var flashMode: Int = ImageCapture.FLASH_MODE_AUTO
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null

    override fun initView() {
        cameraExecutor = Executors.newSingleThreadExecutor()

        iconBack.setOnClickListener { onBackPressed() }
        iconFlash.setOnClickListener { changeFlashMode() }
        iconCapture.setOnClickListener { capturePhoto() }
        buttonAllowPermissions.setOnClickListener {
            requestAllPermissions(
                REQUEST_CODE_PERMISSIONS,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        }

        if (allPermissionsGranted()) {
            setupCamera()
        } else {
            disable(iconCapture, iconFlash)
            visible(labelPermissionsTitle, labelPermissionsSubtitle, buttonAllowPermissions)
        }
    }

    override fun bindViewModel() {
        viewModel.imageLabelingState.observe(this) {
            Timber.d("bindViewModel imageLabelingState: ${it.status}")
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is ErrorStatus -> {
                    //  todo - error bottom sheet
                    showSnackbar(it.errorIdentification.message)
                }
                is SuccessStatus -> {
                    //  TODO show FAB?
                    showFoundIngredientsBottomSheet()
                }
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearImageLabelingCache()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                setupCamera()
            } else {
                showSnackbar(R.string.global_permissions_are_needed)
                finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        return this.checkCameraPermissions() && this.checkReadExternalStoragePermissions()
    }

    private fun changeFlashMode() {
        when (flashMode) {
            ImageCapture.FLASH_MODE_AUTO -> {
                flashMode = ImageCapture.FLASH_MODE_ON
                iconFlash.setImageResource(R.drawable.ic_flash_on)
            }
            ImageCapture.FLASH_MODE_ON -> {
                flashMode = ImageCapture.FLASH_MODE_OFF
                iconFlash.setImageResource(R.drawable.ic_flash_off)
            }
            ImageCapture.FLASH_MODE_OFF -> {
                flashMode = ImageCapture.FLASH_MODE_AUTO
                iconFlash.setImageResource(R.drawable.ic_flash_auto)
            }
        }
        updateCameraConfig()
    }

    private fun updateCameraConfig() {
        if (imageCapture != null) cameraProvider?.unbind(imageCapture)
        imageCapture = ImageCapture.Builder().setFlashMode(flashMode).build()
        cameraProvider?.bindToLifecycle(this, CAMERA_SELECTOR, imageCapture)
    }

    private fun setupCamera() {
        enable(iconCapture, iconFlash)
        gone(labelPermissionsTitle, labelPermissionsSubtitle, buttonAllowPermissions)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraPreview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            //  Used to bind the lifecycle of cameras to the lifecycle owner
            cameraProvider = cameraProviderFuture.get()
            imageCapture = ImageCapture.Builder().setFlashMode(flashMode).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider?.unbindAll()

                // Bind use cases to camera
                cameraProvider?.bindToLifecycle(this, CAMERA_SELECTOR, cameraPreview, imageCapture)
            } catch (e: Exception) {
                Timber.e("Use case binding failed: $e")
                showSnackbar(R.string.error_unknown)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun capturePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = getTemporaryFile()

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                    showSnackbar(R.string.error_unknown)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Timber.d("Photo capture succeeded: ${Uri.fromFile(photoFile)}")
                    val compressedImageUri = compressImageFromUri(Uri.fromFile(photoFile))
                    viewModel.imageLabelingProcess(compressedImageUri)
                    showPhotoPreview(compressedImageUri)
                }
            })
    }

    private fun showPhotoPreview(imageUri: Uri?) {
        gone(iconCapture, iconFlash)
        imageCaptured.setVisible(true)

        Glide.with(this)
            .load(imageUri)
            .centerCrop()
            .addListener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.e("onLoadFailed: $e")
                    showSnackbar(R.string.error_unknown)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.d("onResourceReady:")
                    return false
                }
            })
            .into(imageCaptured)
    }

    private fun showFoundIngredientsBottomSheet() {
        if (ingredientsBottomSheet?.isVisible != true) {
            ingredientsBottomSheet = FoundIngredientsBottomSheet(::selectedIngredients)
            ingredientsBottomSheet?.show(supportFragmentManager, ingredientsBottomSheet?.tag)
        }
    }

    private fun selectedIngredients(selectedIngredients: List<ImageLabelingItem>) {
        viewModel.selectedIngredients(selectedIngredients)
    }
}