package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.CreateRecipeRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AddAllergensObject
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddAllergensViewModel @Inject constructor(
    private val createRecipeRepository: CreateRecipeRepository
) : BaseViewModel() {

    private val selectedAllergens: MutableLiveData<List<String?>> = MutableLiveData<List<String?>>().apply {
        postValue(createRecipeRepository.newRecipe.value?.data?.intolerances ?: emptyList())
    }

    val intolerances: LiveData<List<AddAllergensObject>> =
        Transformations.map(
            combineLatestLiveData(
                createRecipeRepository.allIntolerances,
                selectedAllergens
            )
        ) {
            val tmp = mutableListOf<AddAllergensObject>()

            it.first.forEach { allergen ->
                tmp.add(
                    AddAllergensObject(
                        name = allergen,
                        isChecked = it.second.firstOrNull { selectedAllergen ->
                            selectedAllergen == allergen
                        } != null
                    )
                )
            }

            tmp
        }

    fun addAllergen(allergen: String) {
        val currentAllergens = selectedAllergens.value?.toMutableList() ?: mutableListOf()
        currentAllergens.add(allergen)
        selectedAllergens.postValue(currentAllergens)
    }

    fun removeAllergen(allergen: String) {
        val currentAllergens = selectedAllergens.value?.toMutableList() ?: mutableListOf()
        currentAllergens.remove(allergen)
        selectedAllergens.postValue(currentAllergens)
    }

    fun saveAllergens() {
        val currentAllergens = selectedAllergens.value?.toMutableList() ?: mutableListOf()
        createRecipeRepository.addIntolerances(currentAllergens)
    }
}