package com.lonchi.andrej.lonchi_bakalarka.ui.diets

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDietsBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.diets.adapter.DietRowsAdapter
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DietsFragment : BaseFragment<DietsViewModel, FragmentDietsBinding>() {

    companion object {
        fun newInstance() = DietsFragment()
    }

    override val layoutId: Int = R.layout.fragment_diets
    override val vmClassToken: Class<DietsViewModel> = DietsViewModel::class.java
    override val bindingInflater: (View) -> FragmentDietsBinding =
        { FragmentDietsBinding.bind(it) }

    private val adapterDiets by lazy {
        DietRowsAdapter(
            context = requireContext(),
            removeDiet = { viewModel.removeDiet(it.name) },
            addDiet = { viewModel.addDiet(it.name) }
        )
    }

    override fun initView() {
        (requireActivity() as? MainActivity)?.hideBottomNavigation()
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }
        binding?.buttonSave?.setOnClickListener { saveDiets() }

        binding?.recyclerDiets?.adapter = adapterDiets
        binding?.recyclerDiets?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun bindViewModel() {
        viewModel.diets.observe(viewLifecycleOwner) {
            var numberOfDiets = 0
            it.forEach { diet ->
                if (diet.isChecked) numberOfDiets++
            }
            binding?.chipCounter?.text = numberOfDiets.toString()

            adapterDiets.submitList(it)
        }
    }

    private fun saveDiets() {
        viewModel.saveDiets()
        requireActivity().onBackPressed()
    }
}