package com.lonchi.andrej.lonchi_bakalarka.ui.settings

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentSettingsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutId: Int = R.layout.fragment_settings
    override val vmClassToken: Class<SettingsViewModel> = SettingsViewModel::class.java
    override val bindingInflater: (View) -> FragmentSettingsBinding =
        { FragmentSettingsBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindViewModel() = Unit
}