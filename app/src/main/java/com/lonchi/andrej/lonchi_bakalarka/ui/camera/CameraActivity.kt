package com.lonchi.andrej.lonchi_bakalarka.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.*
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.TorchState.OFF
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.logic.util.*
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity
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

    private var flashMode: Int = ImageCapture.FLASH_MODE_AUTO
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

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
            startCamera()
        } else {
            disable(iconCapture, iconFlash)
            visible(labelPermissionsTitle, labelPermissionsSubtitle, buttonAllowPermissions)
        }
    }

    override fun bindViewModel() = Unit

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                //  todo - snackbar
                Toast.makeText(this, "Need this permissions", Toast.LENGTH_SHORT).show()
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
        //  todo - start camera?
    }

    private fun startCamera() {
        enable(iconCapture, iconFlash)
        gone(labelPermissionsTitle, labelPermissionsSubtitle, buttonAllowPermissions)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //  Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setFlashMode(flashMode)
                .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                // todo - snackbar
                Timber.e("Use case binding failed: $e")
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
                    //  todo - snackbar
                    Timber.e("Photo capture failed: ${exc.message}")
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    // todo showProgressDialog
                    Timber.d("Photo capture succeeded: ${Uri.fromFile(photoFile)}")
                    showPhotoPreview(compressImageFromUri(Uri.fromFile(photoFile)))
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
                    //  todo - snnackbar
                    //  todo - hide progress
                    Timber.e("onLoadFailed: $e")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //  todo - hide progress
                    Timber.d("onResourceReady:")
                    return false
                }
            })
            .into(imageCaptured)
    }
}