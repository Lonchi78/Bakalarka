package com.lonchi.andrej.lonchi_bakalarka.ui.onboarding

import com.google.firebase.auth.FirebaseUser
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    fun login(firebaseUser: FirebaseUser) = userRepository.performUserLogin(firebaseUser)
}
