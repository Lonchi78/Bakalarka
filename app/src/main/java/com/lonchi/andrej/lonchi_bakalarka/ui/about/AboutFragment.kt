package com.lonchi.andrej.lonchi_bakalarka.ui.about

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentAboutBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AboutFragment : BaseFragment<AboutViewModel, FragmentAboutBinding>() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    override val layoutId: Int = R.layout.fragment_about
    override val vmClassToken: Class<AboutViewModel> = AboutViewModel::class.java
    override val bindingInflater: (View) -> FragmentAboutBinding =
        { FragmentAboutBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindViewModel() = Unit
}