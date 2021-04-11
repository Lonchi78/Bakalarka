package com.lonchi.andrej.lonchi_bakalarka.ui.discover.byIngredients

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentIngredientsListBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverByIngredientsListFragment : BaseFragment<DiscoverByIngredientsListViewModel, FragmentIngredientsListBinding>() {

    companion object {
        fun newInstance() = DiscoverByIngredientsListFragment()
    }

    override val layoutId: Int = R.layout.fragment_ingredients_list
    override val vmClassToken: Class<DiscoverByIngredientsListViewModel> = DiscoverByIngredientsListViewModel::class.java
    override val bindingInflater: (View) -> FragmentIngredientsListBinding =
        { FragmentIngredientsListBinding.bind(it) }

    private val adapterIngredients by lazy {
        IngredientRowsDiscoverByAdapter(
            context = requireContext(),
            deleteIngredient = { viewModel.removeIngredient(it) }
        )
    }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonFindRecipes?.setOnClickListener { findRecipes() }
        binding?.buttonAddIngredient?.setOnClickListener { addIngredient() }

        binding?.recyclerIngredients?.adapter = adapterIngredients
        binding?.recyclerIngredients?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.ingredients.observe(viewLifecycleOwner) {
            binding?.chipCounter?.text = it.size.toString()
            adapterIngredients.submitList(it)
        }
    }

    private fun addIngredient() {
        findNavController().navigate(
            DiscoverByIngredientsListFragmentDirections
                .actionIngredientsListFragmentToIngredientsAddFragment()
        )
    }

    private fun findRecipes() {
        findNavController().navigate(
            DiscoverByIngredientsListFragmentDirections
                .actionIngredientsListFragmentToIngredientsResultsFragment()
        )
    }
}