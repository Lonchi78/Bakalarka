package com.lonchi.andrej.lonchi_bakalarka.ui.fragment

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentMainBinding


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainFragment : BaseFragment<FragmentViewModel, FragmentMainBinding>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutId: Int = R.layout.fragment_main
    override val vmClassToken: Class<FragmentViewModel> = FragmentViewModel::class.java
    override val bindingInflater: (View) -> FragmentMainBinding = { FragmentMainBinding.bind(it) }

    override fun initView() = Unit

    override fun bindViewModel() = Unit
}