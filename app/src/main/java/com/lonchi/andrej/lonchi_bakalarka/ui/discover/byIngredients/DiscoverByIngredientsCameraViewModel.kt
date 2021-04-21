package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.ImageLabelingRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingItem
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsCameraViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository,
    private val imageLabelingRepository: ImageLabelingRepository
) : BaseViewModel() {

    val imageLabelingState: MutableLiveData<Resource<ImageLabelingResponse>> =
        imageLabelingRepository.imageLabelingState

    fun selectedIngredients(selectedIngredients: List<ImageLabelingItem>) {
        Timber.d("selectedIngredients: ${selectedIngredients.size}")
        rootRepository.addIngredients(selectedIngredients.map { it.item })
    }

    fun clearImageLabelingCache() {
        imageLabelingRepository.clearImageLabelingCache()
    }

    fun imageLabelingProcess(imageUri: Uri?) {
        imageLabelingRepository.firebaseImageLabelingOnCloud(imageUri)
    }
}