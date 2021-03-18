package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverListBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragmentDirections

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverListFragment : BaseFragment<DiscoverListViewModel, FragmentDiscoverListBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_list
    override val vmClassToken: Class<DiscoverListViewModel> = DiscoverListViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverListBinding = { FragmentDiscoverListBinding.bind(it) }

    private val adapterRecipes by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }

        binding?.queryInput?.setEndIconClickClearInput(true)
        binding?.queryInput?.setPlaceholderText(getString(R.string.discover_search_query_placeholder))
        binding?.queryInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            //  TODO - add mic
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
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
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is SuccessStatus -> {
                    if (it.data != null) {
                        if (it.data.results.isNullOrEmpty()) {
                            //  show empty state
                        } else {
                            adapterRecipes.submitList(it.data.results)
                        }
                    } else showErrorSnackbar(ErrorIdentification.Unknown())
                }
                is ErrorStatus -> showErrorSnackbar(it.errorIdentification)
                else -> Unit
            }
        }
    }

    private fun onRecipeClick(recipe: RecipeItem) {
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getId(),
                idType = recipe.getIdType()
            )
        )
    }
}