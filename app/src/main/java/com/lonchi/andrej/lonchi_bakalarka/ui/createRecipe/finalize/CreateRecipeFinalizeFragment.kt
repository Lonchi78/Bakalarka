package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeFinalizeBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeFinalizeFragment : BaseFragment<CreateRecipeFinalizeViewModel, FragmentCreateRecipeFinalizeBinding>() {
    companion object {
        fun newInstance() = CreateRecipeFinalizeFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_finalize
    override val vmClassToken: Class<CreateRecipeFinalizeViewModel> = CreateRecipeFinalizeViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeFinalizeBinding = { FragmentCreateRecipeFinalizeBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonFinalize?.setOnClickListener { finishCreatingRecipe() }

        binding?.viewAddNutrition?.setOnClickListener { openNutritionScreen() }
        binding?.iconNutritionEdit?.setOnClickListener { openNutritionScreen() }

        binding?.viewAddAllergens?.setOnClickListener { openAllergensScreen() }
        binding?.iconAllergensEdit?.setOnClickListener { openAllergensScreen() }

        binding?.viewAddDiets?.setOnClickListener { openDietsScreen() }
        binding?.iconDietsEdit?.setOnClickListener { openDietsScreen() }
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            if (it.status is SuccessStatus && it.data != null) {
                handleRecipeNutrition(it.data)
                handleRecipeIntolerances(it.data)
                handleRecipeDiets(it.data)
            }
        }
    }

    private fun openNutritionScreen() {
        findNavController().navigate(CreateRecipeFinalizeFragmentDirections.actionFinalizeFragmentToAddNutritionFragment())
    }

    private fun openAllergensScreen() {
        findNavController().navigate(CreateRecipeFinalizeFragmentDirections.actionFinalizeFragmentToAddAllergensFragment())
    }

    private fun openDietsScreen() {
        findNavController().navigate(CreateRecipeFinalizeFragmentDirections.actionFinalizeFragmentToAddDietsFragment())
    }

    private fun finishCreatingRecipe() {
        findNavController().navigate(CreateRecipeFinalizeFragmentDirections.actionFinalizeFragmentToSuccessFragment())
    }

    private fun handleRecipeNutrition(recipe: RecipeItem?) {
        val nutrition = recipe?.getAllNutritions()

        if (nutrition == null || nutrition.nutrients?.isNullOrEmpty() == true) {
            binding?.groupRowNutritionEmpty?.setVisible(true)
            binding?.groupRowNutritionData?.setVisible(false)
        } else {
            binding?.groupRowNutritionEmpty?.setVisible(false)
            binding?.groupRowNutritionData?.setVisible(true)

            binding?.textValueCalories?.text = getString(
                R.string.nutrition_value_calories,
                nutrition.getCalories()?.amount?.toInt() ?: 0
            )
            binding?.textValueProtein?.text = getString(
                R.string.nutrition_value_protein,
                nutrition.getProtein()?.amount?.toInt() ?: 0
            )
            binding?.textValueCarbs?.text = getString(
                R.string.nutrition_value_carbs,
                nutrition.getCarbohydrates()?.amount?.toInt() ?: 0
            )
            binding?.textValueFat?.text = getString(
                R.string.nutrition_value_fat,
                nutrition.getFat()?.amount?.toInt() ?: 0
            )

            binding?.progressBarCalories?.progress =
                nutrition.getCalories()?.percentOfDailyNeeds ?: 0f
            binding?.progressBarProtein?.progress =
                nutrition.getProtein()?.percentOfDailyNeeds ?: 0f
            binding?.progressBarCarbs?.progress =
                nutrition.getCarbohydrates()?.percentOfDailyNeeds ?: 0f
            binding?.progressBarFat?.progress =
                nutrition.getFat()?.percentOfDailyNeeds ?: 0f
        }
    }

    private fun handleRecipeIntolerances(recipe: RecipeItem?) {
        val intolerances = recipe?.getAllIntolerances()

        if (intolerances?.isNullOrEmpty() == null || intolerances.isNullOrEmpty()) {
            binding?.groupRowAllergensEmpty?.setVisible(true)
            binding?.groupRowAllergensData?.setVisible(false)
        } else {
            binding?.groupRowAllergensEmpty?.setVisible(false)
            binding?.groupRowAllergensData?.setVisible(true)

            intolerances.forEach {
                binding?.chipGroupAllergens?.addView(
                    (layoutInflater.inflate(
                        R.layout.chip_single_choice,
                        binding?.chipGroupAllergens,
                        false
                    ) as Chip).apply {
                        text = it
                    }
                )
            }
        }
    }

    private fun handleRecipeDiets(recipe: RecipeItem?) {
        val diets = recipe?.getAllDiets()

        if (diets?.isNullOrEmpty() == null || diets.isNullOrEmpty()) {
            binding?.groupRowDietsEmpty?.setVisible(true)
            binding?.groupRowDietsData?.setVisible(false)
        } else {
            binding?.groupRowDietsEmpty?.setVisible(false)
            binding?.groupRowDietsData?.setVisible(true)

            diets.forEach {
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