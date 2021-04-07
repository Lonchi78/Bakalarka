package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class IngredientsListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    init {
    }

}