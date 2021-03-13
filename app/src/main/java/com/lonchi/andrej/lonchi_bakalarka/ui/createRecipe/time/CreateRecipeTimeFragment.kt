package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.time

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeTimeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeTimeFragment : BaseFragment<CreateRecipeTimeViewModel, FragmentCreateRecipeTimeBinding>() {
    companion object {
        fun newInstance() = CreateRecipeTimeFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_time
    override val vmClassToken: Class<CreateRecipeTimeViewModel> = CreateRecipeTimeViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeTimeBinding = { FragmentCreateRecipeTimeBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeTimeFragmentDirections.actionTimeFragmentToIngredientsFragment())
        }

    }

    override fun bindViewModel() {
    }
}