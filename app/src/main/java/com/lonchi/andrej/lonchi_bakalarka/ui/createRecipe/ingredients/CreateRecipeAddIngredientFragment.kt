package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.ingredients

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddIngredientBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddIngredientFragment : BaseFragment<CreateRecipeAddIngredientViewModel, FragmentCreateRecipeAddIngredientBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddIngredientFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_ingredient
    override val vmClassToken: Class<CreateRecipeAddIngredientViewModel> = CreateRecipeAddIngredientViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddIngredientBinding = { FragmentCreateRecipeAddIngredientBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
    }
}