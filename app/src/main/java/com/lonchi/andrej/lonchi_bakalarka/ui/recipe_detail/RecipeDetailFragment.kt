package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.Recipe
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeItem
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.LoadingStatus
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentRecipeDetailBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.recipes.RecipeCardsAdapter

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
        //  Header
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

        //  Progress bars
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

        //  Action buttons
        binding?.buttonLike?.setOnClickListener {
            //  TODO - detect if is liked or not, handle icon
            binding?.buttonLike?.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_fill_20)
            viewModel.addToFavourites(recipe as? Recipe ?: return@setOnClickListener)
        }
        binding?.buttonShare?.setOnClickListener {
            Toast.makeText(requireContext(), "TODO - share", Toast.LENGTH_SHORT).show()
        }

        //  Ingredients
        binding?.chipCounterIngredients?.text = recipe?.getNumberOfIngredients().toString()
        binding?.recyclerIngredients?.adapter = adapterIngredients
        binding?.recyclerIngredients?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterIngredients.submitList(recipe?.getAllIngredients())
    }
}