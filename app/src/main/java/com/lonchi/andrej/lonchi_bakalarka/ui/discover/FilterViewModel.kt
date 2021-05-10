package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import com.lonchi.andrej.lonchi_bakalarka.data.entities.Filter
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FilterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    val actualFilter = userRepository.actualFilter

    fun saveFilter(filter: Filter) {
        userRepository.saveActualFilter(filter)
    }

    fun resetFilter() {
        userRepository.saveActualFilter(Filter())
    }
}