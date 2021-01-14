package com.lonchi.andrej.lonchi_bakalarka.ui.login

import com.lonchi.andrej.lonchi_bakalarka.data.repository.ImageLabelingRepository
import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val imageLabelingRepository: ImageLabelingRepository
) : BaseViewModel() {

}
