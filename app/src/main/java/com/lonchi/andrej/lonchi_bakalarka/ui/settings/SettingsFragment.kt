package com.lonchi.andrej.lonchi_bakalarka.ui.settings

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentSettingsBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openSendEmail
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openUrlWithCustomTabs
import com.lonchi.andrej.lonchi_bakalarka.ui.about.AboutActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    companion object {
        fun newInstance() = SettingsFragment()

        private const val HELP_EMAIL = "andrejloncik@gmail.com"
        private const val HELP_SUBJECT = "Recipio - Help"
    }

    override val layoutId: Int = R.layout.fragment_settings
    override val vmClassToken: Class<SettingsViewModel> = SettingsViewModel::class.java
    override val bindingInflater: (View) -> FragmentSettingsBinding =
        { FragmentSettingsBinding.bind(it) }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }

        binding?.viewAbout?.setOnClickListener {
            startActivity(AboutActivity.getStartIntent(requireContext()))
        }
        binding?.viewPrivacyPolicy?.setOnClickListener {
            requireActivity().openUrlWithCustomTabs(R.string.global_privacy_policy_url)
        }
        binding?.viewHelp?.setOnClickListener {
            requireActivity().openSendEmail(recipient = HELP_EMAIL, subject = HELP_SUBJECT)
        }
        binding?.textAppVersion?.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

    override fun bindViewModel() = Unit
}