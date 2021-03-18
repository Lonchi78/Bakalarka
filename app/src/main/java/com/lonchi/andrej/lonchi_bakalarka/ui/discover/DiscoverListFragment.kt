package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import android.view.View
import android.widget.Toast
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentDiscoverListBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.hideKeyboard
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openKeyboard
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverListFragment : BaseFragment<DiscoverListViewModel, FragmentDiscoverListBinding>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover_list
    override val vmClassToken: Class<DiscoverListViewModel> = DiscoverListViewModel::class.java
    override val bindingInflater: (View) -> FragmentDiscoverListBinding = { FragmentDiscoverListBinding.bind(it) }

    override fun initView() {
        binding?.iconBack?.setOnClickListener { requireActivity().onBackPressed() }

        binding?.queryInput?.setEndIconClickClearInput(true)
        binding?.queryInput?.setPlaceholderText(getString(R.string.discover_search_query_placeholder))
        binding?.queryInput?.setMicrophoneIconOnClickListener {
            requireActivity().hideKeyboard()
            //  TODO - add mic
            Toast.makeText(requireContext(), "hlasa ze majk", Toast.LENGTH_SHORT).show()
        }

        binding?.queryInput?.requestFocus()
        binding?.queryInput?.getInputField()?.let {
            requireActivity().openKeyboard(it)
        }
    }

    override fun bindViewModel() {
        binding?.queryInput?.setTextObserver(
            delayMillis = 500L,
            action = { viewModel.searchRecipesByQuery(it) }
        )
    }
}