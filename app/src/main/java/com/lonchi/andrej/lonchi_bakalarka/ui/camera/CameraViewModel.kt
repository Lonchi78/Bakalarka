package com.lonchi.andrej.lonchi_bakalarka.ui.camera

import com.lonchi.andrej.lonchi_bakalarka.data.entities.Ingredient
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CameraViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun selectedIngredients(selectedIngredients: List<Ingredient>) {
        Timber.d("selectedIngredients: ${selectedIngredients.size}")
        selectedIngredients.forEach {
            Timber.d("selectedIngredients: $it")
        }
    }

}
