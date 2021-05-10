package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddInstructionBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber

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
        binding?.instructionInput?.setPlaceholderText(getString(R.string.create_recipe_placeholder_instruction))
        binding?.instructionInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            speechToText()
        }
        binding?.instructionInput?.requestFocus()
        binding?.instructionInput?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }
    }

    override fun bindViewModel() {
        binding?.instructionInput?.setTextObserver(
            { binding?.buttonAddInstruction?.isEnabled = it.isNotEmpty() }
        )
    }

    private fun saveInstruction() {
        viewModel.saveInstruction(binding?.instructionInput?.getInputText().orEmpty())
        requireActivity().hideKeyboard()

        binding?.buttonAddInstruction?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {

                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]

                        // Do what you want with the recognized text.
                        Timber.d("STT onActivityResult: $recognizedText")
                        binding?.instructionInput?.setInputText(recognizedText)
                    }
                } else {
                    showErrorSnackbar(getString(R.string.speech_to_text_no_results))
                }
            }
        }
    }
}