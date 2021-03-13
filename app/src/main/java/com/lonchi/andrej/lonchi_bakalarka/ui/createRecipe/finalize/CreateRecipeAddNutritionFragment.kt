package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddNutritionBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

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
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
    }
}