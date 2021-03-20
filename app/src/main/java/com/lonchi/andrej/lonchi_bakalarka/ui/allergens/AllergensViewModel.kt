package com.lonchi.andrej.lonchi_bakalarka.ui.allergens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AddAllergensObject
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AllergensViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val selectedIntolerances: MutableLiveData<List<String?>> = MutableLiveData<List<String?>>().apply {
        postValue(emptyList())
    }

    init {
        compositeDisposable.add(
            userRepository.getUserIntolerancesSingle()
                .subscribe({
                    selectedIntolerances.postValue(it.firstOrNull()?.intolerances ?: emptyList())
                }, {
                    Timber.e("Get user intolerances error: $it")
                })
        )
    }

    val intolerances: LiveData<List<AddAllergensObject>> =
        Transformations.map(
            combineLatestLiveData(
                userRepository.allIntolerances,
                selectedIntolerances
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

    fun addIntolerance(intolerance: String) {
        val currentIntolerance = selectedIntolerances.value?.toMutableList() ?: mutableListOf()
        currentIntolerance.add(intolerance)
        selectedIntolerances.postValue(currentIntolerance)
    }

    fun removeIntolerance(intolerance: String) {
        val currentIntolerance = selectedIntolerances.value?.toMutableList() ?: mutableListOf()
        currentIntolerance.remove(intolerance)
        selectedIntolerances.postValue(currentIntolerance)
    }

    fun saveIntolerances() {
        val currentIntolerance = selectedIntolerances.value?.toMutableList() ?: mutableListOf()
        userRepository.saveUserIntolerances(currentIntolerance)
    }
}