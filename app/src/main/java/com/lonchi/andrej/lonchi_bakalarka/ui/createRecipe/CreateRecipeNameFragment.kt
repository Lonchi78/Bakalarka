package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe

import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeNameBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeNameFragment : BaseFragment<CreateRecipeNameViewModel, FragmentCreateRecipeNameBinding>() {
    companion object {
        fun newInstance() = CreateRecipeNameFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_name
    override val vmClassToken: Class<CreateRecipeNameViewModel> = CreateRecipeNameViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeNameBinding = { FragmentCreateRecipeNameBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeNameFragmentDirections.actionNameFragmentToTimeFragment())
        }

        binding?.searchInput?.setEndIconClickClearInput(true)
        binding?.searchInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
        }
        binding?.searchInput?.requestFocus()
    }

    override fun bindViewModel() {
        //binding?.searchInput?.changeCurrentQuery(viewModel.filterString.value.orEmpty())
        binding?.searchInput?.setTextObserver {
            binding?.buttonNext?.isEnabled = it.isNotEmpty()
            Timber.d("bindViewModel: .searchInput?.setTextObserver = $it")
        }
    }
}