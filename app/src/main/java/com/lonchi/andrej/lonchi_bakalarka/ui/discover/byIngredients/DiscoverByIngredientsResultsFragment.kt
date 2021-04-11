package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverByIngredientsResultsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.RecipeRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragmentDirections
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsResultsFragment : BaseFragment<DiscoverByIngredientsResultsViewModel, FragmentDiscoverByIngredientsResultsBinding>() {

    companion object {
        fun newInstance() = DiscoverByIngredientsResultsFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_by_ingredients_results
    override val vmClassToken: Class<DiscoverByIngredientsResultsViewModel> = DiscoverByIngredientsResultsViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverByIngredientsResultsBinding =
        { FragmentDiscoverByIngredientsResultsBinding.bind(it) }

    private val adapterRecipes by lazy {
        RecipeRowsAdapter(
            context = requireContext(),
            onRecipeClick = { onRecipeClick(it) }
        )
    }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }

        binding?.recyclerRecipes?.adapter = adapterRecipes
        binding?.recyclerRecipes?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.searchRecipes()
        viewModel.searchRecipeState.observe(viewLifecycleOwner) {
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is SuccessStatus -> {
                    if (!it.data.isNullOrEmpty()) {
                        adapterRecipes.submitList(it.data)
                    } else {
                        showErrorSnackbar(ErrorIdentification.Unknown())
                    }
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
                idType = RecipeIdTypeEnum.getRecipeIdType(recipe.getRecipeIdType())
            )
        )
    }
}