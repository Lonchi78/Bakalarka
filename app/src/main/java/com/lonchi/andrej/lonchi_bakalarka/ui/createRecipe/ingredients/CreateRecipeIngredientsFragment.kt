package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeIngredientsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

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

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeIngredientsFragmentDirections.actionIngredientsFragmentToInstructionsFragment())
        }
    }

    override fun bindViewModel() {
    }
}