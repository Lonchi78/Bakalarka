package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients.addIngredient

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsAddViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository
) : BaseViewModel() {

    var saveButtonEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun validateInput(name: String?) {
        saveButtonEnabled.postValue(!name.isNullOrEmpty())
    }

    fun saveIngredient(name: String?) {
        Timber.d("saveIngredient: $name")
        rootRepository.addIngredient(name.orEmpty())
    }

}