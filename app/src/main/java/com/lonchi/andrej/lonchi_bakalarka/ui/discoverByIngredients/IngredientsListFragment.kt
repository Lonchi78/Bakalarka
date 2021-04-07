package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentIngredientsListBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class IngredientsListFragment : BaseFragment<IngredientsListViewModel, FragmentIngredientsListBinding>() {

    companion object {
        fun newInstance() = IngredientsListFragment()
    }

    override val layoutId: Int = R.layout.fragment_ingredients_list
    override val vmClassToken: Class<IngredientsListViewModel> = IngredientsListViewModel::class.java
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
        //viewModel.resetIngredients()

        viewModel.ingredients.observe(viewLifecycleOwner) {
            Timber.d("bindViewModel: ingredients = $it")
            Timber.d("bindViewModel: ingredients = ${it.size}")
            binding?.chipCounter?.text = it.size.toString()
            adapterIngredients.submitList(it)
        }
    }

    private fun addIngredient() {
        findNavController().navigate(IngredientsListFragmentDirections.actionIngredientsListFragmentToIngredientsAddFragment())
    }

    private fun findRecipes() {

    }
}