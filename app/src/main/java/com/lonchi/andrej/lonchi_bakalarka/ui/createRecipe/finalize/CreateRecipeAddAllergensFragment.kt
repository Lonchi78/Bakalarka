package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddAllergensBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddAllergensFragment : BaseFragment<CreateRecipeAddAllergensViewModel, FragmentCreateRecipeAddAllergensBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddAllergensFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_allergens
    override val vmClassToken: Class<CreateRecipeAddAllergensViewModel> = CreateRecipeAddAllergensViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddAllergensBinding = { FragmentCreateRecipeAddAllergensBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
    }
}