package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddNutritionViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    val newRecipe = createRecipeRepository.newRecipe

    val calories = MutableLiveData<Resource<Int>>().apply { postValue(Resource.notStarted()) }
    val fat = MutableLiveData<Resource<Int>>().apply { postValue(Resource.notStarted()) }
    val proteins = MutableLiveData<Resource<Int>>().apply { postValue(Resource.notStarted()) }
    val carbs = MutableLiveData<Resource<Int>>().apply { postValue(Resource.notStarted()) }

    val saveButtonEnabled: LiveData<Boolean> = Transformations.map(
        combineLatestLiveData(
            calories,fat,proteins,carbs
        )
    ) {
        val isEnabled = (it.first?.status is SuccessStatus && it.first.data != null) &&
                (it.second?.status is SuccessStatus && it.second.data != null) &&
                (it.third?.status is SuccessStatus && it.third.data != null) &&
                (it.fourth?.status is SuccessStatus && it.fourth.data != null)
        isEnabled
    }

    fun saveNutrition() {
        createRecipeRepository.addNutrition(
            calories = calories.value?.data ?: 0,
            fat = fat.value?.data ?: 0,
            protein = proteins.value?.data ?: 0,
            carbs = carbs.value?.data ?: 0
        )
    }
}