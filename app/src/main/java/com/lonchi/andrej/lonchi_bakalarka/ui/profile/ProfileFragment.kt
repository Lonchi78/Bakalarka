package com.lonchi.andrej.lonchi_bakalarka.ui.profile

import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class ProfileFragment : BaseFragment<ProfileViewModel>() {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    override val layoutId: Int = R.layout.fragment_profile
    override val vmClassToken: Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}