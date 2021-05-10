package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddIngredientBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddIngredientFragment : BaseFragment<CreateRecipeAddIngredientViewModel, FragmentCreateRecipeAddIngredientBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddIngredientFragment()

        private enum class FragmentInputs {
            INGREDIENT_NAME,
            INGREDIENT_MEASURE
        }
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_ingredient
    override val vmClassToken: Class<CreateRecipeAddIngredientViewModel> = CreateRecipeAddIngredientViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddIngredientBinding = { FragmentCreateRecipeAddIngredientBinding.bind(it) }

    private var speechToTextInput: FragmentInputs? = null


    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonAddIngredient?.setOnClickListener { saveIngredient() }

        binding?.inputIngredientName?.setEndIconClickClearInput(true)
        binding?.inputIngredientName?.setPlaceholderText(getString(R.string.create_recipe_placeholder_ingredient_name))
        binding?.inputIngredientName?.setMicrophoneIconOnClickListener {
            speechToTextInput = FragmentInputs.INGREDIENT_NAME
            requireActivity().hideKeyboard()
            speechToText()
        }
        binding?.inputIngredientName?.requestFocus()
        binding?.inputIngredientName?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }

        binding?.inputIngredientMeasure?.setEndIconClickClearInput(true)
        binding?.inputIngredientMeasure?.setPlaceholderText(getString(R.string.create_recipe_placeholder_ingredient_value))
        binding?.inputIngredientMeasure?.setMicrophoneIconOnClickListener {
            speechToTextInput = FragmentInputs.INGREDIENT_MEASURE
            requireActivity().hideKeyboard()
            speechToText()
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
                        when (speechToTextInput) {
                            FragmentInputs.INGREDIENT_NAME -> binding?.inputIngredientName?.setInputText(recognizedText)
                            FragmentInputs.INGREDIENT_MEASURE -> binding?.inputIngredientMeasure?.setInputText(recognizedText)
                        }
                    }
                } else {
                    showErrorSnackbar(getString(R.string.speech_to_text_no_results))
                }
            }
        }
    }
}