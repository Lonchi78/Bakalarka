package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.chip.Chip
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeFavourite
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentRecipeDetailBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openWebUrl
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeDetailFragment : BaseFragment<RecipeDetailViewModel, FragmentRecipeDetailBinding>() {
    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    override val layoutId: Int = R.layout.fragment_recipe_detail
    override val vmClassToken: Class<RecipeDetailViewModel> = RecipeDetailViewModel::class.java
    override val bindingInflater: (View) -> FragmentRecipeDetailBinding =
        { FragmentRecipeDetailBinding.bind(it) }

    private val adapterIngredients by lazy {
        IngredientRowsAdapter(
            context = requireContext()
        )
    }
    private val adapterInstructions by lazy {
        InstructionRowsAdapter(
            context = requireContext()
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        handleArguments()
        binding?.buttonMealplan?.setOnClickListener {
            findNavController().navigate(
                RecipeDetailFragmentDirections.actionGlobalAddToMealPlannerFragment()
            )
        }
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
        viewModel.stateRecipeDetail.observe(viewLifecycleOwner) {
            showProgressDialog(it.status is LoadingStatus)

            when (it.status) {
                is ErrorStatus -> {
                    //  TODO - show error status
                    showErrorSnackbar(it.errorIdentification)
                }
                is SuccessStatus -> handleRecipeDetail(it.data)
                else -> Unit
            }
        }
    }

    private fun handleArguments() {
        arguments?.let {
            viewModel.handleInputArguments(
                recipeId = RecipeDetailFragmentArgs.fromBundle(it).recipeId,
                recipeIdType = RecipeDetailFragmentArgs.fromBundle(it).idType
            )
        }
    }

    private fun handleRecipeDetail(recipe: RecipeItem?) {
        setupRecipeHeader(recipe)
        setupRecipeNutritions(recipe)
        setupRecipeActionButton(recipe)
        setupRecipeIngredients(recipe)
        setupRecipeInstructions(recipe)
        setupRecipeCuisines(recipe)
        setupRecipeAllergens(recipe)
        setupRecipeDiets(recipe)
        setupRecipeSource(recipe)
    }

    private fun setupRecipeHeader(recipe: RecipeItem?) {
        binding?.image?.load(recipe?.getImageUrl()) {
            placeholder(R.color.gray200)
            error(R.color.gray200)
        }
        binding?.textName?.text = recipe?.getName()
        binding?.textCookingTime?.text = resources.getQuantityString(
            R.plurals.recipe_time_minutes,
            recipe?.getCookingTime() ?: 0,
            recipe?.getCookingTime()
        )
    }

    private fun setupRecipeNutritions(recipe: RecipeItem?) {
        binding?.textValueCalories?.text = getString(
            R.string.nutrition_value_calories,
            recipe?.getAllNutritions()?.getCalories()?.amount?.toInt() ?: 0
        )
        binding?.textValueProtein?.text = getString(
            R.string.nutrition_value_protein,
            recipe?.getAllNutritions()?.getProtein()?.amount?.toInt() ?: 0
        )
        binding?.textValueCarbs?.text = getString(
            R.string.nutrition_value_carbs,
            recipe?.getAllNutritions()?.getCarbohydrates()?.amount?.toInt() ?: 0
        )
        binding?.textValueFat?.text = getString(
            R.string.nutrition_value_fat,
            recipe?.getAllNutritions()?.getFat()?.amount?.toInt() ?: 0
        )

        binding?.progressBarCalories?.progress =
            recipe?.getAllNutritions()?.getCalories()?.percentOfDailyNeeds ?: 0f
        binding?.progressBarProtein?.progress =
            recipe?.getAllNutritions()?.getProtein()?.percentOfDailyNeeds ?: 0f
        binding?.progressBarCarbs?.progress =
            recipe?.getAllNutritions()?.getCarbohydrates()?.percentOfDailyNeeds ?: 0f
        binding?.progressBarFat?.progress =
            recipe?.getAllNutritions()?.getFat()?.percentOfDailyNeeds ?: 0f
    }

    private fun setupRecipeActionButton(recipe: RecipeItem?) {
        Timber.d("setupRecipeActionButton: ${recipe}")
        Timber.d("setupRecipeActionButton: ${recipe?.getRecipeIdType()}")
        Timber.d("setupRecipeActionButton: ${recipe?.getRecipeType()}")
        Timber.d("setupRecipeActionButton: ${RecipeIdTypeEnum.getRecipeIdType(recipe?.getRecipeIdType() ?: -1)}")
        when (recipe?.getRecipeType()) {
            RecipeIdTypeEnum.FAVOURITE_RECIPE -> {
                binding?.buttonLike?.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_fill_20)
                binding?.buttonLike?.setOnClickListener {
                    binding?.buttonLike?.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_empty_20)
                    viewModel.removeFromFavourites(recipe as? RecipeFavourite ?: return@setOnClickListener)
                }
            }
            else -> {
                binding?.buttonLike?.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_empty_20)
                binding?.buttonLike?.setOnClickListener {
                    binding?.buttonLike?.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_fill_20)
                    viewModel.addToFavourites(recipe as? Recipe ?: return@setOnClickListener)
                }
            }
        }
    }

    private fun setupRecipeIngredients(recipe: RecipeItem?) {
        binding?.chipCounterIngredients?.text = recipe?.getNumberOfIngredients().toString()
        binding?.recyclerIngredients?.adapter = adapterIngredients
        binding?.recyclerIngredients?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterIngredients.submitList(recipe?.getAllIngredients())
    }

    private fun setupRecipeInstructions(recipe: RecipeItem?) {
        binding?.chipCounterInstructions?.text = recipe?.getNumberOfInstructions().toString()
        binding?.recyclerInstructions?.adapter = adapterInstructions
        binding?.recyclerInstructions?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterInstructions.submitList(recipe?.getAllInstructions()?.sortedBy { it.number })
    }

    private fun setupRecipeCuisines(recipe: RecipeItem?) {
        if (recipe?.getAllCuisines()?.isNullOrEmpty() == null ||
            recipe.getAllCuisines()?.isNullOrEmpty() == true ) {
            binding?.labelCuisines?.setVisible(false)
            binding?.chipGroupCuisines?.setVisible(false)
        } else {
            binding?.labelCuisines?.setVisible(true)
            binding?.chipGroupCuisines?.setVisible(true)

            recipe.getAllCuisines()?.forEach {
                binding?.chipGroupCuisines?.addView(
                    (layoutInflater.inflate(
                        R.layout.chip_single_choice,
                        binding?.chipGroupCuisines,
                        false
                    ) as Chip).apply {
                        text = it
                    }
                )
            }
        }
    }

    private fun setupRecipeAllergens(recipe: RecipeItem?) {
        //  TODO
    }

    private fun setupRecipeDiets(recipe: RecipeItem?) {
        if (recipe?.getAllDiets()?.isNullOrEmpty() == null ||
            recipe.getAllDiets()?.isNullOrEmpty() == true ) {
            binding?.labelDiets?.setVisible(false)
            binding?.chipGroupDiets?.setVisible(false)
        } else {
            binding?.labelDiets?.setVisible(true)
            binding?.chipGroupDiets?.setVisible(true)

            recipe.getAllDiets()?.forEach {
                binding?.chipGroupDiets?.addView(
                    (layoutInflater.inflate(
                        R.layout.chip_single_choice,
                        binding?.chipGroupDiets,
                        false
                    ) as Chip).apply {
                        text = it
                    }
                )
            }
        }
    }

    private fun setupRecipeSource(recipe: RecipeItem?) {
        binding?.labelSource?.setVisible(recipe?.getRecipeSourceUrl() != null)
        binding?.textSource?.setVisible(recipe?.getRecipeSourceUrl() != null)
        binding?.textSource?.text = recipe?.getRecipeSourceUrl()
        binding?.textSource?.setOnClickListener {
            requireContext().openWebUrl(recipe?.getRecipeSourceUrl() ?: return@setOnClickListener)
        }
    }
}