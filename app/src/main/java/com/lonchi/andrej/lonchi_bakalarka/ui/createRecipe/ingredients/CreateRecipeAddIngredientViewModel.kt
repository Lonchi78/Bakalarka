package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddIngredientViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    var saveButtonEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun validateInput(name: String?, measure: String?) {
        val nameValid = !name.isNullOrEmpty()
        val measureValid = !measure.isNullOrEmpty()
        Timber.d("validateInput: name = $name, nameValid = $nameValid")
        Timber.d("validateInput: measure = $measure, measureValid = $measureValid")
        saveButtonEnabled.postValue(nameValid && measureValid)
    }

    fun saveIngredient(name: String?, measure: String?) {
        createRecipeRepository.addRecipeIngredient(name.orEmpty(), measure.orEmpty())
    }

}