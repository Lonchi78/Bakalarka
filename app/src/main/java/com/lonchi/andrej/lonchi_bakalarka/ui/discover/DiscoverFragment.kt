package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverFragment : BaseFragment<DiscoverViewModel, FragmentDiscoverBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover
    override val vmClassToken: Class<DiscoverViewModel> = DiscoverViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverBinding = { FragmentDiscoverBinding.bind(it) }

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}