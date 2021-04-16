package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddIngredientBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddIngredientFragment : BaseFragment<CreateRecipeAddIngredientViewModel, FragmentCreateRecipeAddIngredientBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddIngredientFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_ingredient
    override val vmClassToken: Class<CreateRecipeAddIngredientViewModel> = CreateRecipeAddIngredientViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddIngredientBinding = { FragmentCreateRecipeAddIngredientBinding.bind(it) }

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
        binding?.inputIngredientName?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }

        binding?.inputIngredientMeasure?.setEndIconClickClearInput(true)
        binding?.inputIngredientMeasure?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            //  TODO - add mic
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
        }
    }

    override fun bindViewModel() {
        binding?.inputIngredientName?.setTextObserver ({
            viewModel.validateInput(
                name = it,
                measure = binding?.inputIngredientMeasure?.getInputText().orEmpty()
            )
        })
        binding?.inputIngredientMeasure?.setTextObserver ({
            viewModel.validateInput(
                name = binding?.inputIngredientName?.getInputText().orEmpty(),
                measure = it
            )
        })

        viewModel.saveButtonEnabled.observe(viewLifecycleOwner) {
            binding?.buttonAddIngredient?.isEnabled = it
        }
    }

    private fun saveIngredient() {
        viewModel.saveIngredient(
            name = binding?.inputIngredientName?.getInputText().orEmpty(),
            measure = binding?.inputIngredientMeasure?.getInputText().orEmpty()
        )

        requireActivity().hideKeyboard()
        binding?.buttonAddIngredient?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }
}