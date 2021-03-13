package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddDietsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddDietsFragment : BaseFragment<CreateRecipeAddDietsViewModel, FragmentCreateRecipeAddDietsBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddDietsFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_diets
    override val vmClassToken: Class<CreateRecipeAddDietsViewModel> = CreateRecipeAddDietsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddDietsBinding = { FragmentCreateRecipeAddDietsBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
    }
}