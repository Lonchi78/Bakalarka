package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.name

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeNameBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeNameFragment : BaseFragment<CreateRecipeNameViewModel, FragmentCreateRecipeNameBinding>() {
    companion object {
        fun newInstance() = CreateRecipeNameFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_name
    override val vmClassToken: Class<CreateRecipeNameViewModel> = CreateRecipeNameViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeNameBinding = { FragmentCreateRecipeNameBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonNext?.setOnClickListener { nextStep() }

        binding?.searchInput?.setPlaceholderText(getString(R.string.create_recipe_placeholder_name))
        binding?.searchInput?.setEndIconClickClearInput(true)
        binding?.searchInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            speechToText()
        }
        binding?.searchInput?.requestFocus()
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            if (it.status is SuccessStatus) {
                val name = it.data?.getName().orEmpty()
                binding?.searchInput?.changeCurrentQuery(name)
                binding?.buttonNext?.isEnabled = name.isNotEmpty()
            }
        }

        binding?.searchInput?.setTextObserver({
            binding?.buttonNext?.isEnabled = it.isNotEmpty()
        })
    }

    private fun nextStep() {
        viewModel.setRecipeName(binding?.searchInput?.getInputText().orEmpty())
        requireActivity().hideKeyboard()
        binding?.label?.postDelayed({
            findNavController().navigate(CreateRecipeNameFragmentDirections.actionNameFragmentToTimeFragment())
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
                        binding?.searchInput?.setInputText(recognizedText)
                    }
                } else {
                    showErrorSnackbar(getString(R.string.speech_to_text_no_results))
                }
            }
        }
    }
}