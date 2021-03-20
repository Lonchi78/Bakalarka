package com.lonchi.andrej.lonchi_bakalarka.ui.diets

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.data.utils.combineLatestLiveData
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter.AddDietsObject
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DietsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val selectedDiets: MutableLiveData<List<String?>> = MutableLiveData<List<String?>>().apply {
        postValue(emptyList())
    }

    init {
        compositeDisposable.add(
            userRepository.getUserDietsSingle()
                .subscribe({
                    selectedDiets.postValue(it.firstOrNull()?.diets ?: emptyList())
                }, {
                    Timber.e("Get user diets error: $it")
                })
        )
    }

    val diets: LiveData<List<AddDietsObject>> =
        Transformations.map(
            combineLatestLiveData(
                userRepository.allDiets,
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
        userRepository.saveUserDiets(currentDiets.toList())
    }

}