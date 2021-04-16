package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddNutritionBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.name.CreateRecipeNameFragmentDirections
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddNutritionFragment : BaseFragment<CreateRecipeAddNutritionViewModel, FragmentCreateRecipeAddNutritionBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddNutritionFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_nutrition
    override val vmClassToken: Class<CreateRecipeAddNutritionViewModel> = CreateRecipeAddNutritionViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddNutritionBinding = { FragmentCreateRecipeAddNutritionBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { saveNutrition() }

        setEditTextCalories()
        setEditTextProteins()
        setEditTextCarbs()
        setEditTextFat()
    }

    private fun setEditTextCalories() {
        binding?.editTextCalories?.setEndIconClickClearInput(true)
        binding?.editTextCalories?.setPlaceholderText((0..500).random().toString())
        binding?.editTextCalories?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        binding?.editTextCalories?.setLabelText(getString(R.string.create_recipe_finalize_add_nutrition_calories))
        binding?.editTextCalories?.setPrefixText(getString(R.string.create_recipe_finalize_add_nutrition_calories_unit))
        binding?.editTextCalories?.requestFocus()
        binding?.editTextCalories?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }
    }

    private fun setEditTextProteins() {
        binding?.editTextProteins?.setEndIconClickClearInput(true)
        binding?.editTextProteins?.setPlaceholderText((0..80).random().toString())
        binding?.editTextProteins?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        binding?.editTextProteins?.setLabelText(getString(R.string.create_recipe_finalize_add_nutrition_proteins))
        binding?.editTextProteins?.setPrefixText(getString(R.string.create_recipe_finalize_add_nutrition_proteins_unit))
    }

    private fun setEditTextCarbs() {
        binding?.editTextCarbs?.setEndIconClickClearInput(true)
        binding?.editTextCarbs?.setPlaceholderText((0..150).random().toString())
        binding?.editTextCarbs?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        binding?.editTextCarbs?.setLabelText(getString(R.string.create_recipe_finalize_add_nutrition_carbs))
        binding?.editTextCarbs?.setPrefixText(getString(R.string.create_recipe_finalize_add_nutrition_carbs_unit))
    }

    private fun setEditTextFat() {
        binding?.editTextFat?.setEndIconClickClearInput(true)
        binding?.editTextFat?.setPlaceholderText((0..40).random().toString())
        binding?.editTextFat?.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
        binding?.editTextFat?.setLabelText(getString(R.string.create_recipe_finalize_add_nutrition_fat))
        binding?.editTextFat?.setPrefixText(getString(R.string.create_recipe_finalize_add_nutrition_fat_unit))
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            if (it.status is SuccessStatus && it.data != null) handleCurrentRecipe(it.data)
        }
        viewModel.saveButtonEnabled.observe(viewLifecycleOwner) {
            binding?.buttonSave?.isEnabled = it
        }

        binding?.editTextCalories?.setTextObserver {
            try {
                viewModel.calories.postValue(Resource.success(it.toInt()))
            } catch (e: Exception) {
                Timber.d("editTextProteins setTextObserver exception! $e")
            }
        }
        binding?.editTextCarbs?.setTextObserver {
            try {
                viewModel.carbs.postValue(Resource.success(it.toInt()))
            } catch (e: Exception) {
                Timber.d("editTextProteins setTextObserver exception! $e")
            }
        }
        binding?.editTextFat?.setTextObserver {
            try {
                viewModel.fat.postValue(Resource.success(it.toInt()))
            } catch (e: Exception) {
                Timber.d("editTextProteins setTextObserver exception! $e")
            }
        }
        binding?.editTextProteins?.setTextObserver {
            try {
                viewModel.proteins.postValue(Resource.success(it.toInt()))
            } catch (e: Exception) {
                Timber.d("editTextProteins setTextObserver exception! $e")
            }
        }
    }

    private fun handleCurrentRecipe(recipe: RecipeCustom) {
        recipe.nutrition?.getCalories()?.amount?.toInt()?.let {
            binding?.editTextCalories?.changeCurrentQuery(it.toString())
            viewModel.calories.postValue(Resource.success(it))
        }
        recipe.nutrition?.getCarbohydrates()?.amount?.toInt()?.let {
            binding?.editTextCarbs?.changeCurrentQuery(it.toString())
            viewModel.carbs.postValue(Resource.success(it))
        }
        recipe.nutrition?.getProtein()?.amount?.toInt()?.let {
            binding?.editTextProteins?.changeCurrentQuery(it.toString())
            viewModel.proteins.postValue(Resource.success(it))
        }
        recipe.nutrition?.getFat()?.amount?.toInt()?.let {
            binding?.editTextFat?.changeCurrentQuery(it.toString())
            viewModel.fat.postValue(Resource.success(it))
        }
    }

    private fun saveNutrition() {
        viewModel.saveNutrition()
        requireActivity().hideKeyboard()
        binding?.toolbar?.postDelayed({
            requireActivity().onBackPressed()
        }, resources.getInteger(R.integer.hide_keyboard_delay).toLong())
    }
}