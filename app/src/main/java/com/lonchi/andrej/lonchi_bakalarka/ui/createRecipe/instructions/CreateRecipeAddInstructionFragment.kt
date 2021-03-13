package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddInstructionBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddInstructionFragment : BaseFragment<CreateRecipeAddInstructionViewModel, FragmentCreateRecipeAddInstructionBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddInstructionFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_instruction
    override val vmClassToken: Class<CreateRecipeAddInstructionViewModel> = CreateRecipeAddInstructionViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddInstructionBinding = { FragmentCreateRecipeAddInstructionBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonAddInstruction?.setOnClickListener { saveInstruction() }

        binding?.instructionInput?.setEndIconClickClearInput(true)
        binding?.instructionInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            //  TODO - add mic
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
        }
        binding?.instructionInput?.requestFocus()
    }

    override fun bindViewModel() {
        binding?.instructionInput?.setTextObserver {
            binding?.buttonAddInstruction?.isEnabled = it.isNotEmpty()
        }
    }

    private fun saveInstruction() {
        viewModel.saveInstruction(binding?.instructionInput?.getInputText().orEmpty())
        requireActivity().hideKeyboard()

        binding?.buttonAddInstruction?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }
}