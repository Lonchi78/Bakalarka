package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import androidx.core.content.ContextCompat
import coil.load
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverFragment : BaseFragment<DiscoverViewModel, FragmentDiscoverBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover
    override val vmClassToken: Class<DiscoverViewModel> = DiscoverViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverBinding = { FragmentDiscoverBinding.bind(it) }

    override fun initView() {
        binding?.ingredient1?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_avocado)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient2?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_cheese)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient3?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_red_onion)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient4?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_avocado)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient5?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_cheese)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient6?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_red_onion)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }

        binding?.quickSearch1?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_avocado)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.quickSearch2?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_cheese)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.quickSearch3?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_red_onion)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.quickSearch4?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_avocado)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.quickSearch5?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_cheese)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.quickSearch6?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_red_onion)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
    }

    override fun bindViewModel() = Unit
}