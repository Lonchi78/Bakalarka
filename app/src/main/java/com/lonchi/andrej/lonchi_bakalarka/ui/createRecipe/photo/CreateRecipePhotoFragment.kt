package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.photo

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipePhotoBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.instructions.CreateRecipeInstructionsViewModel

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipePhotoFragment : BaseFragment<CreateRecipeInstructionsViewModel, FragmentCreateRecipePhotoBinding>() {
    companion object {
        fun newInstance() = CreateRecipePhotoFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_photo
    override val vmClassToken: Class<CreateRecipeInstructionsViewModel> = CreateRecipeInstructionsViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipePhotoBinding = { FragmentCreateRecipePhotoBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipePhotoFragmentDirections.actionPhotoFragmentToFinalizeFragment())
        }
    }

    override fun bindViewModel() {
    }
}