package com.lonchi.andrej.lonchi_skeleton.ui.discover

import com.lonchi.andrej.lonchi_skeleton.R
import com.lonchi.andrej.lonchi_skeleton.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverFragment : BaseFragment<DiscoverViewModel>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover
    override val vmClassToken: Class<DiscoverViewModel> = DiscoverViewModel::class.java

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}