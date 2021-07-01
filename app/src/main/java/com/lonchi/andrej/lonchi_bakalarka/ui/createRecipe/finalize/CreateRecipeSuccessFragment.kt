package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.app.Activity.RESULT_OK
import android.content.Intent
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

        const val KEY_CREATED_CUSTOM_RECIPE_ID = "key.created.custom.recipe.id"
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_success
    override val vmClassToken: Class<CreateRecipeSuccessViewModel> = CreateRecipeSuccessViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeSuccessBinding = { FragmentCreateRecipeSuccessBinding.bind(it) }

    override fun initView() {
        binding?.buttonHome?.setOnClickListener {
            requireActivity().finish()
        }
        binding?.buttonShowRecipe?.setOnClickListener {
            viewModel.newRecipe.value?.data?.getId()?.let {
                val data = Intent().apply {
                    putExtra(KEY_CREATED_CUSTOM_RECIPE_ID, it)
                }
                requireActivity().setResult(RESULT_OK, data)
                requireActivity().finish()
            }
        }
    }

    override fun bindViewModel() = Unit
}