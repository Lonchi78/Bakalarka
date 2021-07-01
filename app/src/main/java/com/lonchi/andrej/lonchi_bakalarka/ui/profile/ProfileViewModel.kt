package com.lonchi.andrej.lonchi_bakalarka.ui.profile

import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun logout() = userRepository.performUserLogout()

    fun test() = userRepository.updateUserData()

}