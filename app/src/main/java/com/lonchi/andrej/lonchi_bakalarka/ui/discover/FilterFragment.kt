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
        binding?.iconReset?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { requireActivity().onBackPressed() }

        setupRanges()
    }

    override fun bindViewModel() = Unit

    private fun setupRanges() {
        binding?.rangeCalories?.addOnChangeListener { rangeSlider, _, _ ->
            rangeSlider.values.getOrNull(0)?.let {
                binding?.textCaloriesMin?.text = getString(R.string.filter_gram_value, it.toInt())
            }
            rangeSlider.values.getOrNull(1)?.let {
                binding?.textCaloriesMax?.text = getString(R.string.filter_gram_value, it.toInt())
            }
        }

        binding?.rangeProtein?.addOnChangeListener { rangeSlider, _, _ ->
            rangeSlider.values.getOrNull(0)?.let {
                binding?.textProteinMin?.text = getString(R.string.filter_gram_value, it.toInt())
            }
            rangeSlider.values.getOrNull(1)?.let {
                binding?.textProteinMax?.text = getString(R.string.filter_gram_value, it.toInt())
            }
        }

        binding?.rangeFat?.addOnChangeListener { rangeSlider, _, _ ->
            rangeSlider.values.getOrNull(0)?.let {
                binding?.textFatMin?.text = getString(R.string.filter_gram_value, it.toInt())
            }
            rangeSlider.values.getOrNull(1)?.let {
                binding?.textFatMax?.text = getString(R.string.filter_gram_value, it.toInt())
            }
        }

        binding?.rangeCarbs?.addOnChangeListener { rangeSlider, _, _ ->
            rangeSlider.values.getOrNull(0)?.let {
                binding?.textCarbsMin?.text = getString(R.string.filter_gram_value, it.toInt())
            }
            rangeSlider.values.getOrNull(1)?.let {
                binding?.textCarbsMax?.text = getString(R.string.filter_gram_value, it.toInt())
            }
        }
    }
}