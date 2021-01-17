package com.lonchi.andrej.lonchi_bakalarka.ui.favourites

import android.view.View
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentFavouritesBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class FavouritesFragment : BaseFragment<FavouritesViewModel, FragmentFavouritesBinding>() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    override val layoutId: Int = R.layout.fragment_favourites
    override val vmClassToken: Class<FavouritesViewModel> = FavouritesViewModel::class.java
    override val bindingInflater: (View) -> FragmentFavouritesBinding =
        { FragmentFavouritesBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.chipCounter?.text = (0..10).random().toString()
    }

    override fun bindViewModel() = Unit
}