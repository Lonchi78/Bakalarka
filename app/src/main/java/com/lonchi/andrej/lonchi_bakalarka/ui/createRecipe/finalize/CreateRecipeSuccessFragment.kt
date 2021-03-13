package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeSuccessBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeSuccessFragment : BaseFragment<CreateRecipeSuccessViewModel, FragmentCreateRecipeSuccessBinding>() {
    companion object {
        fun newInstance() = CreateRecipeSuccessFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_success
    override val vmClassToken: Class<CreateRecipeSuccessViewModel> = CreateRecipeSuccessViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeSuccessBinding = { FragmentCreateRecipeSuccessBinding.bind(it) }

    override fun initView() {
        binding?.buttonFinalize?.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun bindViewModel() {
    }
}