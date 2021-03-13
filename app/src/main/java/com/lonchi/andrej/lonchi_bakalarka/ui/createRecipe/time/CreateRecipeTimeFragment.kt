package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.time

import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeTimeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CreateRecipeTimeFragment : BaseFragment<CreateRecipeTimeViewModel, FragmentCreateRecipeTimeBinding>() {
    companion object {
        fun newInstance() = CreateRecipeTimeFragment()
    }

    override val layoutId: Int = R.layout.fragment_create_recipe_time
    override val vmClassToken: Class<CreateRecipeTimeViewModel> = CreateRecipeTimeViewModel::class.java
    override val bindingInflater: (View) -> FragmentCreateRecipeTimeBinding = { FragmentCreateRecipeTimeBinding.bind(it) }

    override fun initView() {
        binding?.buttonBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding?.buttonNext?.setOnClickListener {
            findNavController().navigate(CreateRecipeTimeFragmentDirections.actionTimeFragmentToIngredientsFragment())
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding?.timePicker?.hour = 6
            binding?.timePicker?.minute = 20
        } else {
            binding?.timePicker?.currentHour = 6
            binding?.timePicker?.currentMinute = 20
        }

        binding?.timePicker?.setIs24HourView(true)
        binding?.timePicker?.setOnTimeChangedListener { _, hour, minute ->
            binding?.buttonNext?.isEnabled = (hour != 0) || (minute != 0)
            binding?.textTime?.text = getString(R.string.create_recipe_time_input, hour, minute)
        }
    }

    override fun bindViewModel() {
    }
}