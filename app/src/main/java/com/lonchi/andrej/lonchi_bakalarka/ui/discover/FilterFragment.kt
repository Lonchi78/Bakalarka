package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Filter
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
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
        binding?.iconReset?.setOnClickListener { viewModel.resetFilter() }
        binding?.buttonSave?.setOnClickListener { saveFilter() }

        setupRanges()
    }

    override fun bindViewModel() {
        viewModel.actualFilter.observe(viewLifecycleOwner) {
            if (it.status is SuccessStatus && it.data != null) handleActualFilter(it.data)
            else handleActualFilter(Filter())
        }
    }

    private fun handleActualFilter(filter: Filter) {
        binding?.switchDiets?.isChecked = filter.includeDiets
        binding?.switchIntolerances?.isChecked = filter.includeIntolerances

        binding?.rangeCalories?.values = listOf(filter.caloriesMin.toFloat(), filter.caloriesMax.toFloat())
        binding?.textCaloriesMin?.text = getString(R.string.filter_gram_value, filter.caloriesMin)
        binding?.textCaloriesMax?.text = getString(R.string.filter_gram_value, filter.caloriesMax)

        binding?.rangeProtein?.values = listOf(filter.proteinMin.toFloat(), filter.proteinMax.toFloat())
        binding?.textProteinMin?.text = getString(R.string.filter_gram_value, filter.proteinMin)
        binding?.textProteinMax?.text = getString(R.string.filter_gram_value, filter.proteinMax)

        binding?.rangeFat?.values = listOf(filter.fatMin.toFloat(), filter.fatMax.toFloat())
        binding?.textFatMin?.text = getString(R.string.filter_gram_value, filter.fatMin)
        binding?.textFatMax?.text = getString(R.string.filter_gram_value, filter.fatMax)

        binding?.rangeCarbs?.values = listOf(filter.carbsMin.toFloat(), filter.carbsMax.toFloat())
        binding?.textCarbsMin?.text = getString(R.string.filter_gram_value, filter.carbsMin)
        binding?.textCarbsMax?.text = getString(R.string.filter_gram_value, filter.carbsMax)
    }

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

    private fun saveFilter() {
        val filter = Filter().apply {
            binding?.switchDiets?.isChecked?.let { this.includeDiets = it }
            binding?.switchIntolerances?.isChecked?.let { this.includeIntolerances = it }

            binding?.rangeCalories?.values?.getOrNull(0)?.let{ this.caloriesMin = it.toInt() }
            binding?.rangeCalories?.values?.getOrNull(1)?.let{ this.caloriesMax = it.toInt() }

            binding?.rangeProtein?.values?.getOrNull(0)?.let{ this.proteinMin = it.toInt() }
            binding?.rangeProtein?.values?.getOrNull(1)?.let{ this.proteinMax = it.toInt() }

            binding?.rangeFat?.values?.getOrNull(0)?.let{ this.fatMin = it.toInt() }
            binding?.rangeFat?.values?.getOrNull(1)?.let{ this.fatMax = it.toInt() }

            binding?.rangeCarbs?.values?.getOrNull(0)?.let{ this.carbsMin = it.toInt() }
            binding?.rangeCarbs?.values?.getOrNull(1)?.let{ this.carbsMax = it.toInt() }
        }
        viewModel.saveFilter(filter)

        binding?.toolbar?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }
}