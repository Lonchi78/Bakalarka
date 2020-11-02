package com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet

import androidx.lifecycle.MutableLiveData
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrej.loncik@dactylgroup.com>
 */
class FoundIngredientsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val foundIngredients: MutableLiveData<List<Ingredient>> = MutableLiveData<List<Ingredient>>().apply {
        postValue(mockData())
    }

    private fun mockData(): List<Ingredient> {
        val tmp = mutableListOf<Ingredient>()
        for (i in 0..2) {
            tmp.add(
                Ingredient(i.toLong(), "Ingredient $i")
            )
        }
        return tmp
    }
}