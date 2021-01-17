package com.lonchi.andrej.lonchi_bakalarka.ui.allergens

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentAllergensBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AllergensFragment : BaseFragment<AllergensViewModel, FragmentAllergensBinding>() {

    companion object {
        fun newInstance() = AllergensFragment()
    }

    override val layoutId: Int = R.layout.fragment_allergens
    override val vmClassToken: Class<AllergensViewModel> = AllergensViewModel::class.java
    override val bindingInflater: (View) -> FragmentAllergensBinding =
        { FragmentAllergensBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.chipCounter?.text = (0..10).random().toString()
    }

    override fun bindViewModel() = Unit
}