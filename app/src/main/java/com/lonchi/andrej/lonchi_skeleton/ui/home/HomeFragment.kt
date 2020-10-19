package com.lonchi.andrej.lonchi_skeleton.ui.home

import com.lonchi.andrej.lonchi_skeleton.R
import com.lonchi.andrej.lonchi_skeleton.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel>() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}