package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import com.lonchi.andrej.lonchi_bakalarka.data.repository.DiscoverByIngredientsRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverViewModel @Inject constructor(
    private val rootRepository: DiscoverByIngredientsRepository
) : BaseViewModel() {

    fun resetIngredients() {
        rootRepository.resetIngredients()
    }
}