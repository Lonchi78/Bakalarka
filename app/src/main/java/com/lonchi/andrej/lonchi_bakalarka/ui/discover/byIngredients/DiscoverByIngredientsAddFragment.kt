package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverByIngredientsAddBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber


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
        binding?.inputIngredientName?.setMicrophoneIconVisibility(false)
        binding?.inputIngredientName?.requestFocus()
        binding?.inputIngredientName?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }

        binding?.buttonImageInput?.setOnClickListener {
            requireActivity().hideKeyboard()
            findNavController().navigate(
                DiscoverByIngredientsAddFragmentDirections.actionIngredientsAddFragmentToIngredientsCameraFragment()
            )
        }
        binding?.buttonVoiceInput?.setOnClickListener {
            requireActivity().hideKeyboard()
            speechToText()
        }
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
                        binding?.inputIngredientName?.setInputText(recognizedText)
                    }
                } else {
                    showErrorSnackbar(getString(R.string.speech_to_text_no_results))
                }
            }
        }
    }
}