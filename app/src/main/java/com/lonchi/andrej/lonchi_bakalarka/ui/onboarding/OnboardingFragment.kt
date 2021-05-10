package com.lonchi.andrej.lonchi_bakalarka.ui.onboarding

import android.os.Bundle
import android.view.View
import coil.load
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentOnboardingBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class OnboardingFragment : BaseFragment<OnboardingViewModel, FragmentOnboardingBinding>() {

    companion object {
        fun newInstance(position: Int? = null): OnboardingFragment {
            val fragment = OnboardingFragment()
            position?.let {
                fragment.arguments = Bundle().apply { putInt(KEY_PARAM_POSITION, position) }
            }
            return fragment
        }

        private const val KEY_PARAM_POSITION = "key.param.position"
    }

    override val layoutId: Int = R.layout.fragment_onboarding
    override val vmClassToken: Class<OnboardingViewModel> = OnboardingViewModel::class.java
    override val bindingInflater: (View) -> FragmentOnboardingBinding = { FragmentOnboardingBinding.bind(it) }

    override fun initView() {
        arguments?.getInt(KEY_PARAM_POSITION)?.let {
            handleFragmentPosition(it)
        }
    }

    override fun bindViewModel() = Unit

    private fun handleFragmentPosition(position: Int) {
        when (position) {
            0 -> {
                binding?.text?.text = getString(R.string.onboarding_1_discover_by_query)
                binding?.image?.load(R.drawable.onboarding_discover) {
                    placeholder(R.color.gray50)
                    error(R.color.gray50)
                }
            }
            1 -> {
                binding?.text?.text = getString(R.string.onboarding_2_discover_by_ingredients)
                binding?.image?.load(R.drawable.onboarding_discover_by_ingredients) {
                    placeholder(R.color.gray50)
                    error(R.color.gray50)
                }
            }
            2 -> {
                binding?.text?.text = getString(R.string.onboarding_3_recipe_detail)
                binding?.image?.load(R.drawable.onboarding_recipe_detail_cut) {
                    placeholder(R.color.gray50)
                    error(R.color.gray50)
                }
            }
            3 -> {
                binding?.text?.text = getString(R.string.onboarding_4_create_recipe)
                binding?.image?.load(R.drawable.onboarding_instruction) {
                    placeholder(R.color.gray50)
                    error(R.color.gray50)
                }
            }
        }
    }
}