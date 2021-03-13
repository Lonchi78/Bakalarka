package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.RecipesRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeTimeViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    val newRecipe = createRecipeRepository.newRecipe

    var hours: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }
    var minutes: MutableLiveData<Int> = MutableLiveData<Int>().apply { postValue(0) }

    var cookingTime: LiveData<Pair<Int, Int>> = Transformations.map(
        combineLatestLiveData(hours, minutes)
    ) {
        Pair(it.first, it.second)
    }

    fun setRecipeCookingTime() {
        createRecipeRepository.setRecipeCookingTime(
            hours = hours.value ?: 0,
            minutes = minutes.value ?: 0
        )
    }
}