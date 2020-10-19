package com.lonchi.andrej.lonchi_bakalarka.ui.fragment

import com.lonchi.andrej.lonchi_bakalarka.data.repository.preferences.SharedPreferencesInterface
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewModel
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FragmentViewModel @Inject constructor(
    private val persist: SharedPreferencesInterface
) : BaseViewModel() {}
