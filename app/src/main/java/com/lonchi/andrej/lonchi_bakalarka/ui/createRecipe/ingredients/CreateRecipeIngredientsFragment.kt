package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeIngredientsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeIngredientsFragment : BaseFragment<CreateRecipeIngredientsViewModel, FragmentCreateRecipeIngredientsBinding>() {
    companion object {
        fun newInstance() = CreateRecipeIngredientsFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_ingredients
    override val vmClassToken: Class<CreateRecipeIngredientsViewModel> = CreateRecipeIngredientsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeIngredientsBinding = { FragmentCreateRecipeIngredientsBinding.bind(it) }

    private val adapterIngredients by lazy {
        IngredientRowsCreateRecipeAdapter(
            context = requireContext(),
            deleteIngredient = { viewModel.deleteIngredient(it) },
            editIngredient = {
                Timber.d("edit ingredient: $it")
            }
        )
    }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeIngredientsFragmentDirections.actionIngredientsFragmentToInstructionsFragment())
        }
        binding?.buttonAddIngredients?.setOnClickListener {
            findNavController().navigate(CreateRecipeIngredientsFragmentDirections.actionIngredientsFragmentToAddIngredientsFragment())
        }

        binding?.recyclerIngredients?.adapter = adapterIngredients
        binding?.recyclerIngredients?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            handleRecipeIngredients(it)
        }
    }

    private fun handleRecipeIngredients(recipe: Resource<RecipeCustom>) {
        //  TODO - allow to edit instructions
        binding?.buttonNext?.isEnabled = (recipe.data?.getNumberOfIngredients() ?: 0) != 0

        if (recipe.status is SuccessStatus) {
            adapterIngredients.submitList(recipe.data?.getAllIngredients()?.sortedBy { it.name })
        }
    }
}