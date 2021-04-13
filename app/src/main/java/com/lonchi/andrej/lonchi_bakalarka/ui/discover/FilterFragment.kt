package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentFilterBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FilterFragment : BaseFragment<FilterViewModel, FragmentFilterBinding>() {
    companion object {
        fun newInstance() = FilterFragment()
    }

    override val layoutId: Int = R.layout.fragment_filter
    override val vmClassToken: Class<FilterViewModel> = FilterViewModel::class.java
    override val bindingInflater: (View) -> FragmentFilterBinding = { FragmentFilterBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindViewModel() = Unit
}