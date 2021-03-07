package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverListBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverListFragment : BaseFragment<DiscoverListViewModel, FragmentDiscoverListBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_list
    override val vmClassToken: Class<DiscoverListViewModel> = DiscoverListViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverListBinding = { FragmentDiscoverListBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() = Unit
}