package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeAddInstructionBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeAddInstructionFragment : BaseFragment<CreateRecipeAddInstructionViewModel, FragmentCreateRecipeAddInstructionBinding>() {
    companion object {
        fun newInstance() = CreateRecipeAddInstructionFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_add_instruction
    override val vmClassToken: Class<CreateRecipeAddInstructionViewModel> = CreateRecipeAddInstructionViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeAddInstructionBinding = { FragmentCreateRecipeAddInstructionBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun bindViewModel() {
    }
}