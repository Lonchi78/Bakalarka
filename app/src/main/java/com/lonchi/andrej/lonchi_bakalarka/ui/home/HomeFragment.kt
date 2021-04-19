package com.lonchi.andrej.lonchi_bakalarka.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.chip.Chip
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.repository.rest.JokeResponse
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentHomeBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.getGreetingText
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
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
        private const val LIMIT_CARDS_ADAPTER = 5
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
        viewModel.randomIngredients.observe(viewLifecycleOwner) {
            handleRandomIngredients(it)
        }
        viewModel.stateRandomJoke.observe(viewLifecycleOwner) {
            handleRandomJoke(it)
        }
        viewModel.getAllFavouritesRecipes().observe(viewLifecycleOwner) {
            handleFavouriteRecipes(it)
        }
        viewModel.getAllCustomRecipes().observe(viewLifecycleOwner) {
            handleOwnRecipes(it)
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

    private fun handleRandomIngredients(ingredients: List<String>) {
        ingredients.forEach {
            binding?.chipGroupIngredients?.addView(
                (layoutInflater.inflate(
                    R.layout.chip_single_choice,
                    binding?.chipGroupIngredients,
                    false
                ) as Chip).apply {
                    text = it
                }
            )
        }
    }

    private fun handleRandomJoke(jokeResponse: Resource<JokeResponse>) {
        when {
            jokeResponse.status is SuccessStatus && jokeResponse.data?.joke != null -> {
                binding?.textJoke?.setVisible(true)
                binding?.textJoke?.text = jokeResponse.data.joke
            }
            else -> binding?.textJoke?.setVisible(false)
        }
    }

    private fun handleFavouriteRecipes(recipes: List<Recipe>) {
        binding?.chipCounterFavourites?.text = recipes.size.toString()

        when {
            recipes.isEmpty() -> {
                binding?.emptyLayoutFavouriteRecipes?.setVisible(true)
                binding?.recyclerFavouriteRecipes?.setVisible(false)
            }
            recipes.size >= LIMIT_CARDS_ADAPTER -> {
                binding?.emptyLayoutFavouriteRecipes?.setVisible(false)
                binding?.recyclerFavouriteRecipes?.setVisible(true)
                adapterFavouriteRecipes.submitList(recipes.subList(0, LIMIT_CARDS_ADAPTER - 1))
            }
            else -> {
                binding?.emptyLayoutFavouriteRecipes?.setVisible(false)
                binding?.recyclerFavouriteRecipes?.setVisible(true)
                adapterFavouriteRecipes.submitList(recipes)
            }
        }
        adapterFavouriteRecipes.submitList(recipes)
    }

    private fun handleOwnRecipes(recipes: List<Recipe>) {
        binding?.chipCounterOwnRecipes?.text = recipes.size.toString()

        when {
            recipes.isEmpty() -> {
                binding?.recyclerOwnRecipes?.setVisible(false)
            }
            recipes.size >= LIMIT_CARDS_ADAPTER -> {
                binding?.recyclerOwnRecipes?.setVisible(true)
                adapterOwnRecipes.submitList(recipes.subList(0, LIMIT_CARDS_ADAPTER - 1))
            }
            else -> {
                binding?.recyclerOwnRecipes?.setVisible(true)
                adapterOwnRecipes.submitList(recipes)
            }
        }
    }

}