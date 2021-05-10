package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byQuery

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverByQueryBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.DiscoverFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.RecipeDiscoverRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByQueryFragment : BaseFragment<DiscoverByQueryViewModel, FragmentDiscoverByQueryBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_by_query
    override val vmClassToken: Class<DiscoverByQueryViewModel> = DiscoverByQueryViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverByQueryBinding = { FragmentDiscoverByQueryBinding.bind(it) }

    private val adapterRecipes by lazy {
        RecipeDiscoverRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.iconFilter?.setOnClickListener { openFilterScreen() }

        binding?.queryInput?.setEndIconClickClearInput(true)
        binding?.queryInput?.setPlaceholderText(getString(R.string.discover_search_query_placeholder))
        binding?.queryInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            speechToText()
        }

        binding?.queryInput?.requestFocus()
        binding?.queryInput?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }

        binding?.recyclerRecipes?.adapter = adapterRecipes
        binding?.recyclerRecipes?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        binding?.queryInput?.setTextObserver(
            delayMillis = 250L,
            action = {
                if (it.length > 2) viewModel.searchRecipesByQuery(it)
            }
        )

        viewModel.searchRecipeState.observe(viewLifecycleOwner) {
            binding?.progressBarSearching?.setVisible(it.status is LoadingStatus)

            when (it.status) {
                is LoadingStatus -> {
                    binding?.labelEmptyResults?.setVisible(false)
                }
                is SuccessStatus -> {
                    if (it.data != null) {
                        if (it.data.results.isNullOrEmpty()) {
                            binding?.labelEmptyResults?.setVisible(true)
                            binding?.recyclerRecipes?.setVisible(false)
                        } else {
                            binding?.labelEmptyResults?.setVisible(false)
                            binding?.recyclerRecipes?.setVisible(true)
                            adapterRecipes.submitList(it.data.results)
                        }
                    } else showErrorSnackbar(ErrorIdentification.Unknown())
                }
                is ErrorStatus -> {
                    showErrorSnackbar(it.errorIdentification)
                }
                else -> Unit
            }
        }
    }

    private fun openFilterScreen() {
        findNavController().navigate(
            DiscoverByQueryFragmentDirections.actionGlobalFilterFragment()
        )
    }

    private fun onRecipeClick(recipe: RecipeItem) {
        findNavController().navigate(
            DiscoverByQueryFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getId(),
                idType = RecipeIdTypeEnum.getRecipeIdType(recipe.getRecipeIdType())
            )
        )
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
                        binding?.queryInput?.setInputText(recognizedText)
                    }
                } else {
                    showErrorSnackbar(getString(R.string.speech_to_text_no_results))
                }
            }
        }
    }
}