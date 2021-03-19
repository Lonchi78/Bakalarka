package com.lonchi.andrej.lonchi_bakalarka.ui.favourites

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeFavourite
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentFavouritesBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.RecipeRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.home.HomeFragmentDirections
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FavouritesFragment : BaseFragment<FavouritesViewModel, FragmentFavouritesBinding>() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    override val layoutId: Int = R.layout.fragment_favourites
    override val vmClassToken: Class<FavouritesViewModel> = FavouritesViewModel::class.java
    override val bindingInflater: (View) -> FragmentFavouritesBinding =
        { FragmentFavouritesBinding.bind(it) }

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
        viewModel.getAllFavouritesRecipes().observe(viewLifecycleOwner) {
            handleRecipes(it)
        }
    }

    private fun handleRecipes(recipes: List<RecipeFavourite>) {
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
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getId(),
                idType = RecipeIdTypeEnum.FAVOURITE_RECIPE
            )
        )
    }
}