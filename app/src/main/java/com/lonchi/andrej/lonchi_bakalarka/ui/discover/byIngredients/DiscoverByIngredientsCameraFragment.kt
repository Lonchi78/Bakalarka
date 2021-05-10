package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.Manifest
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverByIngredientsCameraBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.*
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.bottom_sheet.FoundIngredientsBottomSheet
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsCameraFragment :
    BaseFragment<DiscoverByIngredientsCameraViewModel, FragmentDiscoverByIngredientsCameraBinding>() {

    companion object {
        fun newInstance() = DiscoverByIngredientsCameraFragment()
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override val layoutId: Int = R.layout.fragment_discover_by_ingredients_camera
    override val vmClassToken: Class<DiscoverByIngredientsCameraViewModel> =
        DiscoverByIngredientsCameraViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverByIngredientsCameraBinding =
        { FragmentDiscoverByIngredientsCameraBinding.bind(it) }

    private var ingredientsBottomSheet: FoundIngredientsBottomSheet? = null
    private lateinit var cameraExecutor: ExecutorService

    private val CAMERA_SELECTOR = CameraSelector.DEFAULT_BACK_CAMERA
    private var flashMode: Int = ImageCapture.FLASH_MODE_AUTO
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null

    override fun initView() {
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding?.iconReset?.setOnClickListener { resetPhotoPreview() }
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.iconFlash?.setOnClickListener { changeFlashMode() }
        binding?.iconCapture?.setOnClickListener { capturePhoto() }
        binding?.buttonAllowPermissions?.setOnClickListener {
            requestAllPermissions(
                REQUEST_CODE_PERMISSIONS,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        }

        if (allPermissionsGranted()) {
            setupCamera()
        } else {
            disable(binding?.iconCapture, binding?.iconFlash)
            visible(
                binding?.labelPermissionsTitle,
                binding?.labelPermissionsSubtitle,
                binding?.buttonAllowPermissions
            )
        }
    }

    override fun bindViewModel() {
        viewModel.imageLabelingState.observe(this) {
            Timber.d("bindViewModel imageLabelingState: ${it.status}")
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is ErrorStatus -> {
                    //  todo - error bottom sheet
                    showErrorSnackbar(it.errorIdentification.message)
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                setupCamera()
            } else {
                showErrorSnackbar(getString(R.string.global_permissions_are_needed))
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        return requireContext().checkCameraPermissions() &&
                requireContext().checkReadExternalStoragePermissions()
    }

    private fun changeFlashMode() {
        when (flashMode) {
            ImageCapture.FLASH_MODE_AUTO -> {
                flashMode = ImageCapture.FLASH_MODE_ON
                binding?.iconFlash?.setImageResource(R.drawable.ic_flash_on)
            }
            ImageCapture.FLASH_MODE_ON -> {
                flashMode = ImageCapture.FLASH_MODE_OFF
                binding?.iconFlash?.setImageResource(R.drawable.ic_flash_off)
            }
            ImageCapture.FLASH_MODE_OFF -> {
                flashMode = ImageCapture.FLASH_MODE_AUTO
                binding?.iconFlash?.setImageResource(R.drawable.ic_flash_auto)
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
        enable(binding?.iconCapture, binding?.iconFlash)
        gone(
            binding?.labelPermissionsTitle,
            binding?.labelPermissionsSubtitle,
            binding?.buttonAllowPermissions
        )
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraPreview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding?.viewFinder?.surfaceProvider)
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
                showErrorSnackbar(getString(R.string.error_unknown))
            }
        }, ContextCompat.getMainExecutor(requireContext()))
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
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}")
                    showErrorSnackbar(getString(R.string.error_unknown))
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Timber.d("Photo capture succeeded: ${Uri.fromFile(photoFile)}")
                    val compressedImageUri =
                        requireContext().compressImageFromUri(Uri.fromFile(photoFile))
                    viewModel.imageLabelingProcess(compressedImageUri)
                    showPhotoPreview(compressedImageUri)
                }
            })
    }

    private fun showPhotoPreview(imageUri: Uri?) {
        gone(binding?.iconCapture, binding?.iconFlash)
        binding?.imageCaptured?.setVisible(true)

        binding?.imageCaptured?.let {
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
                        showErrorSnackbar(getString(R.string.error_unknown))
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
                .into(it)
        }
    }

    private fun resetPhotoPreview() {
        visible(binding?.iconCapture, binding?.iconFlash)
        binding?.imageCaptured?.setVisible(false)
    }

    private fun showFoundIngredientsBottomSheet() {
        if (ingredientsBottomSheet?.isVisible != true) {
            ingredientsBottomSheet = FoundIngredientsBottomSheet(::confirmIngredients)
            ingredientsBottomSheet?.show(
                requireActivity().supportFragmentManager,
                ingredientsBottomSheet?.tag
            )
        }
    }

    private fun confirmIngredients(selectedIngredients: List<ImageLabelingItem>) {
        viewModel.selectedIngredients(selectedIngredients)
        binding?.iconBack?.postDelayed({
            findNavController().navigate(
                DiscoverByIngredientsCameraFragmentDirections.actionIngredientsCameraFragmentPopupToIngredientsListFragment()
            )
        }, 300)
    }
}