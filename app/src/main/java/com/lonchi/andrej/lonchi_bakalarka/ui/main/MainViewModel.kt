package com.lonchi.andrej.lonchi_bakalarka.ui.main

import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {
    fun getFirstUse(): Boolean = userRepository.getFirstStart()
    fun updateFirstStart(value: Boolean) = userRepository.updateFirstStart(value)
}
