package com.lonchi.andrej.lonchi_bakalarka.ui.discoverByIngredients

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentAllergensBinding
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentIngredientsListBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AllergenRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment


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

    private val adapterAllergens by lazy {
        AllergenRowsAdapter(
            context = requireContext(),
            removeAllergen = { viewModel.removeIntolerance(it.name) },
            addAllergen = { viewModel.addIntolerance(it.name) }
        )
    }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonFindRecipes?.setOnClickListener { saveIntolerances() }

        binding?.recyclerIngredients?.adapter = adapterAllergens
        binding?.recyclerIngredients?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.intolerances.observe(viewLifecycleOwner) {
            var numberOfIntolerances = 0
            it.forEach { diet ->
                if (diet.isChecked) numberOfIntolerances++
            }
            binding?.chipCounter?.text = numberOfIntolerances.toString()

            adapterAllergens.submitList(it)
        }
    }

    private fun saveIntolerances() {
        viewModel.saveIntolerances()
        requireActivity().onBackPressed()
    }
}