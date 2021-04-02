package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.custom

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentRecipeDetailCustomBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.IngredientRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.InstructionRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail.RecipeDetailFragmentArgs

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeDetailCustomFragment : BaseFragment<RecipeDetailCustomViewModel, FragmentRecipeDetailCustomBinding>() {
    companion object {
        fun newInstance() = RecipeDetailCustomFragment()
    }

    override val layoutId: Int = R.layout.fragment_recipe_detail_custom
    override val vmClassToken: Class<RecipeDetailCustomViewModel> = RecipeDetailCustomViewModel::class.java
    override val bindingInflater: (View) -> FragmentRecipeDetailCustomBinding =
        { FragmentRecipeDetailCustomBinding.bind(it) }

    private val adapterIngredients by lazy {
        IngredientRowsAdapter(
            context = requireContext(),
            isCustom = true
        )
    }
    private val adapterInstructions by lazy {
        InstructionRowsAdapter(
            context = requireContext()
        )
    }

    override fun initView() {
        handleArguments()

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
        setupRecipeIngredients(recipe)
        setupRecipeInstructions(recipe)
        setupRecipeIntolerances(recipe)
        setupRecipeDiets(recipe)
    }

    private fun setupRecipeHeader(recipe: RecipeItem?) {
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

    private fun setupRecipeIntolerances(recipe: RecipeItem?) {
        if (recipe?.getAllIntolerances()?.isNullOrEmpty() == null ||
            recipe.getAllIntolerances()?.isNullOrEmpty() == true ) {
            binding?.labelIntolerances?.setVisible(false)
            binding?.chipGroupIntolerances?.setVisible(false)
        } else {
            binding?.labelIntolerances?.setVisible(true)
            binding?.chipGroupIntolerances?.setVisible(true)

            recipe.getAllIntolerances()?.forEach {
                binding?.chipGroupIntolerances?.addView(
                    (layoutInflater.inflate(
                        R.layout.chip_single_choice,
                        binding?.chipGroupIntolerances,
                        false
                    ) as Chip).apply {
                        text = it
                    }
                )
            }
        }
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
}