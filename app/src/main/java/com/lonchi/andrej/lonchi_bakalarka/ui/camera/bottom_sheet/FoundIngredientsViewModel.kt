package com.lonchi.andrej.lonchi_bakalarka.ui.camera.bottom_sheet

import com.lonchi.andrej.lonchi_bakalarka.data.repository.UserRepository
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrej.loncik@dactylgroup.com>
 */
class FoundIngredientsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

}