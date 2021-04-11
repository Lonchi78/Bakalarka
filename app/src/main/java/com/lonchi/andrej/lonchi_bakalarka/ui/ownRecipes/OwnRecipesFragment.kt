package com.lonchi.andrej.lonchi_bakalarka.ui.ownRecipes

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentOwnRecipesBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byQuery.RecipeByQueryRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragmentDirections
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class OwnRecipesFragment : BaseFragment<OwnRecipesViewModel, FragmentOwnRecipesBinding>() {

    companion object {
        fun newInstance() = OwnRecipesFragment()
    }

    override val layoutId: Int = R.layout.fragment_own_recipes
    override val vmClassToken: Class<OwnRecipesViewModel> = OwnRecipesViewModel::class.java
    override val bindingInflater: (View) -> FragmentOwnRecipesBinding =
        { FragmentOwnRecipesBinding.bind(it) }

    private val adapterRecipes by lazy {
        RecipeByQueryRowsAdapter(
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
        viewModel.getAllCustomRecipes().observe(viewLifecycleOwner) {
            handleRecipes(it)
        }
    }

    private fun handleRecipes(recipes: List<RecipeCustom>) {
        binding?.chipCounter?.text = recipes.size.toString()

        if (recipes.isNullOrEmpty()) {
            //  TODO - add empty state
        } else {
            //  TODO - hide added empty state
            adapterRecipes.submitList(recipes)
        }
    }

    private fun onRecipeClick(recipe: RecipeItem) {
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailCustomFragment(
                recipeId = recipe.getId(),
                idType = RecipeIdTypeEnum.OWN_RECIPE
            )
        )
    }
}