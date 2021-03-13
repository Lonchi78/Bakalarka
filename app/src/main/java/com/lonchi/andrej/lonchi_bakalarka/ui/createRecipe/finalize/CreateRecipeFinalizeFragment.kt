package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.finalize

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeFinalizeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeFinalizeFragment : BaseFragment<CreateRecipeFinalizeViewModel, FragmentCreateRecipeFinalizeBinding>() {
    companion object {
        fun newInstance() = CreateRecipeFinalizeFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_finalize
    override val vmClassToken: Class<CreateRecipeFinalizeViewModel> = CreateRecipeFinalizeViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeFinalizeBinding = { FragmentCreateRecipeFinalizeBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonFinalize?.setOnClickListener {
            findNavController().navigate(CreateRecipeFinalizeFragmentDirections.actionFinalizeFragmentToSuccessFragment())
        }
    }

    override fun bindViewModel() {
    }
}