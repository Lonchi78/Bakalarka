package com.lonchi.andrej.lonchi_bakalarka.ui.ownRecipes

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentOwnRecipesBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class OwnRecipesFragment : BaseFragment<OwnRecipesViewModel, FragmentOwnRecipesBinding>() {

    companion object {
        fun newInstance() = OwnRecipesFragment()
    }

    override val layoutId: Int = R.layout.fragment_own_recipes
    override val vmClassToken: Class<OwnRecipesViewModel> = OwnRecipesViewModel::class.java
    override val bindingInflater: (View) -> FragmentOwnRecipesBinding =
        { FragmentOwnRecipesBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.chipCounter?.text = (0..10).random().toString()
    }

    override fun bindViewModel() = Unit
}