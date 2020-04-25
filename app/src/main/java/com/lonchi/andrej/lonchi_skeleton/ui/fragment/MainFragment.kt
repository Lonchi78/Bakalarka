package com.lonchi.andrej.lonchi_skeleton.ui.fragment

import com.lonchi.andrej.lonchi_skeleton.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_skeleton.R


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainFragment : BaseFragment<FragmentViewModel>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutId: Int = R.layout.fragment_main
    override val vmClassToken: Class<FragmentViewModel> = FragmentViewModel::class.java

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}