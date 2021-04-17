package com.lonchi.andrej.lonchi_bakalarka.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.getGreetingText
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.CreateRecipeActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize.CreateRecipeSuccessFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeIdTypeEnum
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsColumnAdapter


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()

        private const val REQUEST_CODE_CREATE_CUSTOM_RECIPE = 100
    }

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java
    override val bindingInflater: (View) -> FragmentHomeBinding = { FragmentHomeBinding.bind(it) }

    private val adapterRandomRecipes by lazy {
        RecipeCardsAdapter(
            context = requireContext(),
            onItemClick = { onRecipeItemClick(it) }
        )
    }

    private val adapterFavouriteRecipes by lazy {
        RecipeCardsColumnAdapter(
            context = requireContext(),
            onItemClick = { onRecipeItemClick(it) }
        )
    }

    private val adapterOwnRecipes by lazy {
        RecipeCardsColumnAdapter(
            context = requireContext(),
            onItemClick = { onRecipeItemClick(it) }
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.showBottomNavigation()
        binding?.textGreetings?.text = requireContext().getGreetingText()

        binding?.recyclerRandomRecipes?.adapter = adapterRandomRecipes
        binding?.recyclerRandomRecipes?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        LinearSnapHelper().attachToRecyclerView(binding?.recyclerRandomRecipes)

        binding?.recyclerFavouriteRecipes?.adapter = adapterFavouriteRecipes
        binding?.recyclerFavouriteRecipes?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        ).apply {
            isMeasurementCacheEnabled = false
        }

        binding?.recyclerOwnRecipes?.adapter = adapterOwnRecipes
        binding?.recyclerOwnRecipes?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        ).apply {
            isMeasurementCacheEnabled = false
        }

        binding?.buttonCreateRecipe?.setOnClickListener {
            startActivityForResult(
                CreateRecipeActivity.getStartIntent(requireContext()),
                REQUEST_CODE_CREATE_CUSTOM_RECIPE
            )
        }
    }

    override fun bindViewModel() {
        viewModel.stateRandomRecipes.observe(viewLifecycleOwner) {
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is SuccessStatus -> {
                    adapterRandomRecipes.submitList(it.data)
                    adapterFavouriteRecipes.submitList(it.data)
                    adapterOwnRecipes.submitList(it.data)
                    binding?.chipCounterFavourites?.text = (0..10).random().toString()
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CREATE_CUSTOM_RECIPE && resultCode == RESULT_OK) {
            data?.getStringExtra(CreateRecipeSuccessFragment.KEY_CREATED_CUSTOM_RECIPE_ID)?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalRecipeDetailCustomFragment(
                        recipeId = it,
                        idType = RecipeIdTypeEnum.OWN_RECIPE
                    )
                )
            }
        }
    }

    private fun onRecipeItemClick(recipe: RecipeItem) {
        when (recipe.getRecipeType()) {
            RecipeIdTypeEnum.FAVOURITE_RECIPE -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.FAVOURITE_RECIPE
                    )
                )
            }
            RecipeIdTypeEnum.OWN_RECIPE -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.OWN_RECIPE
                    )
                )
            }
            else -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalRecipeDetailFragment(
                        recipeId = recipe.getId(),
                        idType = RecipeIdTypeEnum.REST
                    )
                )
            }
        }
    }

}