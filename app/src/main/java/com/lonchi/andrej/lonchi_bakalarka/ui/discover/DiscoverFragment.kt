package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import coil.load
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients.DiscoverByIngredientActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.profile.ProfileFragmentDirections
import timber.log.Timber

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

        binding?.searchBar?.setOnClickListener {
            findNavController().navigate(DiscoverFragmentDirections.actionDiscoverFragmentToDiscoverListFragment())
        }

        binding?.chipSearchByIngredients?.setOnClickListener {
            startActivity(DiscoverByIngredientActivity.getStartIntent(requireContext()))
        }
    }

    override fun bindViewModel() = Unit
}