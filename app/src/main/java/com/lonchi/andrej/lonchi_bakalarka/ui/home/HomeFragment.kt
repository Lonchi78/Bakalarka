package com.lonchi.andrej.lonchi_bakalarka.ui.home

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsAdapter

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java
    override val bindingInflater: (View) -> FragmentHomeBinding = { FragmentHomeBinding.bind(it) }

    private val adapterRandomRecipes by lazy {
        RecipeCardsAdapter(
            context = requireContext(),
            onItemClick = { onRestRecipeClick(it) }
        )
    }

    override fun initView() {
        binding?.recyclerRandomRecipes?.adapter = adapterRandomRecipes
        binding?.recyclerRandomRecipes?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        LinearSnapHelper().attachToRecyclerView(binding?.recyclerRandomRecipes)

        binding?.buttonCreateRecipe?.setOnClickListener {
            startActivity(CameraActivity.getStartIntent(requireContext()))
        }
    }

    override fun bindViewModel() {
        viewModel.stateRandomRecipes.observe(viewLifecycleOwner) {
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is SuccessStatus -> adapterRandomRecipes.submitList(it.data)
                is ErrorStatus -> {
                    //  TODO - add error state or hide section, show connectivity problem
                    showErrorSnackbar(it.errorIdentification)
                }
                is LoadingStatus -> {
                    //  TODO - show loading status?
                }
                else -> Unit
            }
        }
    }

    private fun onRestRecipeClick(recipe: Recipe) {
        Toast.makeText(requireContext(), "onItemClick ${recipe.title}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getRecipeId(),
                idType = RecipeIdTypeEnum.REST
            )
        )
    }

    private fun onFavouriteRecipeClick(recipe: Recipe) {
        Toast.makeText(requireContext(), "onItemClick ${recipe.title}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getRecipeId(),
                idType = RecipeIdTypeEnum.FAVOURITE_RECIPE
            )
        )
    }

    private fun onOwnRecipeClick(recipe: Recipe) {
        Toast.makeText(requireContext(), "onItemClick ${recipe.title}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                recipeId = recipe.getRecipeId(),
                idType = RecipeIdTypeEnum.OWN_RECIPE
            )
        )
    }

}