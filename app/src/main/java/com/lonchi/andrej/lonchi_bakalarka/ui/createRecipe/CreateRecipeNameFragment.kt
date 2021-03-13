package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeNameBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeNameFragment : BaseFragment<CreateRecipeNameViewModel, FragmentCreateRecipeNameBinding>() {
    companion object {
        fun newInstance() = CreateRecipeNameFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_name
    override val vmClassToken: Class<CreateRecipeNameViewModel> = CreateRecipeNameViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeNameBinding = { FragmentCreateRecipeNameBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeNameFragmentDirections.actionNameFragmentToTimeFragment())
        }
    }

    override fun bindViewModel() = Unit
}