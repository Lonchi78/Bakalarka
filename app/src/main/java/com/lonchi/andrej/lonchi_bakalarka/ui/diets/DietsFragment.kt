package com.lonchi.andrej.lonchi_bakalarka.ui.diets

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDietsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DietsFragment : BaseFragment<DietsViewModel, FragmentDietsBinding>() {

    companion object {
        fun newInstance() = DietsFragment()
    }

    override val layoutId: Int = R.layout.fragment_diets
    override val vmClassToken: Class<DietsViewModel> = DietsViewModel::class.java
    override val bindingInflater: (View) -> FragmentDietsBinding =
        { FragmentDietsBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.chipCounter?.text = (0..10).random().toString()
    }

    override fun bindViewModel() = Unit
}