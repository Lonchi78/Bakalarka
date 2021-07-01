package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter.AddDietsObject
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddDietsViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    private val selectedDiets: MutableLiveData<List<String?>> = MutableLiveData<List<String?>>().apply {
        postValue(createRecipeRepository.newRecipe.value?.data?.diets ?: emptyList())
    }

    val diets: LiveData<List<AddDietsObject>> =
        Transformations.map(
            combineLatestLiveData(
                createRecipeRepository.allDiets,
                selectedDiets
            )
        ) {
            val tmp = mutableListOf<AddDietsObject>()

            it.first.forEach { diet ->
                tmp.add(
                    AddDietsObject(
                        name = diet,
                        isChecked = it.second.firstOrNull { selectedDiet ->
                            selectedDiet == diet
                        } != null
                    )
                )
            }

            tmp
        }

    fun addDiet(diet: String) {
        val currentDiets = selectedDiets.value?.toMutableList() ?: mutableListOf()
        currentDiets.add(diet)
        selectedDiets.postValue(currentDiets)
    }

    fun removeDiet(diet: String) {
        val currentDiets = selectedDiets.value?.toMutableList() ?: mutableListOf()
        currentDiets.remove(diet)
        selectedDiets.postValue(currentDiets)
    }

    fun saveDiets() {
        val currentDiets = selectedDiets.value?.toMutableList() ?: mutableListOf()
        createRecipeRepository.addDiets(currentDiets)
    }
}