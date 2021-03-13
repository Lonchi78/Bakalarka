package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeInstructionsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeInstructionsFragment : BaseFragment<CreateRecipeInstructionsViewModel, FragmentCreateRecipeInstructionsBinding>() {
    companion object {
        fun newInstance() = CreateRecipeInstructionsFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_instructions
    override val vmClassToken: Class<CreateRecipeInstructionsViewModel> = CreateRecipeInstructionsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeInstructionsBinding = { FragmentCreateRecipeInstructionsBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeInstructionsFragmentDirections.actionInstructionsFragmentToPhotoFragment())
        }
    }

    override fun bindViewModel() {
    }
}