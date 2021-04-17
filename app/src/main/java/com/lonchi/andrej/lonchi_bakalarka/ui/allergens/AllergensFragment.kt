package com.lonchi.andrej.lonchi_bakalarka.ui.allergens

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentAllergensBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.allergens.adapter.AllergenRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AllergensFragment : BaseFragment<AllergensViewModel, FragmentAllergensBinding>() {

    companion object {
        fun newInstance() = AllergensFragment()
    }

    override val layoutId: Int = R.layout.fragment_allergens
    override val vmClassToken: Class<AllergensViewModel> = AllergensViewModel::class.java
    override val bindingInflater: (View) -> FragmentAllergensBinding =
        { FragmentAllergensBinding.bind(it) }

    private val adapterAllergens by lazy {
        AllergenRowsAdapter(
            context = requireContext(),
            removeAllergen = { viewModel.removeIntolerance(it.name) },
            addAllergen = { viewModel.addIntolerance(it.name) }
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { saveIntolerances() }

        binding?.recyclerIntolerances?.adapter = adapterAllergens
        binding?.recyclerIntolerances?.layoutManager =
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