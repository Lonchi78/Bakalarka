package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository
) : BaseViewModel() {

    fun addIngredient(value: String) = rootRepository.addIngredient(value)

    fun resetIngredients() {
        rootRepository.resetIngredients()
    }
}
