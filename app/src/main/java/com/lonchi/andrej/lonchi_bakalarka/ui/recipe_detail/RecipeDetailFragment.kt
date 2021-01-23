package com.lonchi.andrej.lonchi_bakalarka.ui.recipe_detail

import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentRecipeDetailBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class RecipeDetailFragment : BaseFragment<RecipeDetailViewModel, FragmentRecipeDetailBinding>() {
    companion object {
        fun newInstance() = RecipeDetailFragment()
    }

    override val layoutId: Int = R.layout.fragment_recipe_detail
    override val vmClassToken: Class<RecipeDetailViewModel> = RecipeDetailViewModel::class.java
    override val bindingInflater: (View) -> FragmentRecipeDetailBinding = { FragmentRecipeDetailBinding.bind(it) }

    override fun initView() {
        arguments?.let { viewModel.recipeId = RecipeDetailFragmentArgs.fromBundle(it).recipeId }
        arguments?.let { viewModel.recipeIdType = RecipeDetailFragmentArgs.fromBundle(it).idType }
        Toast.makeText(requireContext(), "ID = ${viewModel.recipeId}, type = ${viewModel.recipeIdType}", Toast.LENGTH_SHORT).show()
    }

    override fun bindViewModel() = Unit
}