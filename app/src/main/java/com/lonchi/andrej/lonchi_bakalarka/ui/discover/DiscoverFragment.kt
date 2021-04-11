package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import coil.load
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients.DiscoverByIngredientsActivity

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
        setupPreviewIngredients()

        binding?.searchBar?.setOnClickListener {
            findNavController().navigate(DiscoverFragmentDirections.actionDiscoverFragmentToDiscoverListFragment())
        }

        binding?.chipSearchByIngredients?.setOnClickListener {
            discoverRecipesByIngredients()
        }
    }

    override fun bindViewModel() = Unit

    private fun setupPreviewIngredients() {
        //  TODO - add some dynamic to random select six ingredients from set of ingredients

        binding?.ingredient1?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_cheese)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient2?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_chicken)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient3?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_tomato)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient4?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_avocado)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient5?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_red_onion)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }
        binding?.ingredient6?.imageIngredient?.load(ContextCompat.getDrawable(requireContext(), R.drawable.img_egg)) {
            placeholder(R.color.gray50)
            error(R.color.gray50)
        }

        binding?.ingredient1?.textIngredientName?.text = getString(R.string.discover_example_ingredient_cheese)
        binding?.ingredient2?.textIngredientName?.text = getString(R.string.discover_example_ingredient_chicken)
        binding?.ingredient3?.textIngredientName?.text = getString(R.string.discover_example_ingredient_tomato)
        binding?.ingredient4?.textIngredientName?.text = getString(R.string.discover_example_ingredient_avocado)
        binding?.ingredient5?.textIngredientName?.text = getString(R.string.discover_example_ingredient_red_onion)
        binding?.ingredient6?.textIngredientName?.text = getString(R.string.discover_example_ingredient_egg)

        binding?.ingredient1?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_cheese)
        }
        binding?.ingredient2?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_chicken)
        }
        binding?.ingredient3?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_tomato)
        }
        binding?.ingredient4?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_avocado)
        }
        binding?.ingredient5?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_red_onion)
        }
        binding?.ingredient6?.layoutCard?.setOnClickListener {
            discoverRecipesByIngredients(R.string.discover_example_ingredient_egg)
        }
    }

    private fun discoverRecipesByIngredients(previewIngredient: Int? = null) {
        startActivity(
            DiscoverByIngredientsActivity.getStartIntent(
                context = requireContext(),
                previewIngredient = previewIngredient?.let { getString(it) }
            )
        )
    }
}