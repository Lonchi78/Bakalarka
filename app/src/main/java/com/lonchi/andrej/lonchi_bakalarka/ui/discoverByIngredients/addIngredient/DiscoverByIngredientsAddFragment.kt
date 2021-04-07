package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients.addIngredient

import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverByIngredientsAddBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsAddFragment : BaseFragment<DiscoverByIngredientsAddViewModel, FragmentDiscoverByIngredientsAddBinding>() {
    companion object {
        fun newInstance() = DiscoverByIngredientsAddFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_by_ingredients_add
    override val vmClassToken: Class<DiscoverByIngredientsAddViewModel> = DiscoverByIngredientsAddViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverByIngredientsAddBinding = { FragmentDiscoverByIngredientsAddBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonAddIngredient?.setOnClickListener { saveIngredient() }

        binding?.inputIngredientName?.setEndIconClickClearInput(true)
        binding?.inputIngredientName?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            //  TODO - add mic
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
        }
        binding?.inputIngredientName?.requestFocus()
    }

    override fun bindViewModel() {
        binding?.inputIngredientName?.setTextObserver ({
            viewModel.validateInput(it)
        })

        viewModel.saveButtonEnabled.observe(viewLifecycleOwner) {
            binding?.buttonAddIngredient?.isEnabled = it
        }
    }

    private fun saveIngredient() {
        viewModel.saveIngredient(
            name = binding?.inputIngredientName?.getInputText().orEmpty()
        )

        requireActivity().hideKeyboard()
        binding?.buttonAddIngredient?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }
}