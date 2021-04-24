package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.bottom_sheet

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.ImageLabelingRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.ImageLabelingResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrej.loncik@dactylgroup.com>
 */
class FoundIngredientsViewModel @Inject constructor(
    private val imageLabelingRepository: ImageLabelingRepository
) : BaseViewModel() {

    val imageLabelingState: MutableLiveData<Resource<ImageLabelingResponse>> =
        imageLabelingRepository.imageLabelingState

}