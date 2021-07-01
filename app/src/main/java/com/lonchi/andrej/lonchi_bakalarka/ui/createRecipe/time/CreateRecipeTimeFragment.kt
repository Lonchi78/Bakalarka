package com.lonchi.andrej.lonchi_bakalarka.ui.createRecipe.time

import android.os.Build
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.entities.RecipeCustom
import com.lonchi.andrej.lonchi_bakalarka.data.utils.Resource
import com.lonchi.andrej.lonchi_bakalarka.data.utils.SuccessStatus
import com.lonchi.andrej.lonchi_bakalarka.databinding.FragmentCreateRecipeTimeBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit

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
            nextStep()
        }

        binding?.timePicker?.setIs24HourView(true)
        binding?.timePicker?.setOnTimeChangedListener { _, hour, minute ->
            Timber.d("bindViewModel: cookingTime hours = $hour")
            Timber.d("bindViewModel: cookingTime mins = $minute")
            Timber.d("bindViewModel: cookingTime * * *")
            viewModel.hours.postValue(hour)
            viewModel.minutes.postValue(minute)
        }
    }

    override fun bindViewModel() {
        viewModel.newRecipe.observe(viewLifecycleOwner) {
            handleCurrentCookingTime(it)
        }
        viewModel.cookingTime.observe(viewLifecycleOwner) {
            Timber.d("bindViewModel: cookingTime hours = ${it.first}")
            Timber.d("bindViewModel: cookingTime mins = ${it.second}")
            Timber.d("bindViewModel: cookingTime * * *")
            binding?.textTime?.text = getString(R.string.create_recipe_time_input, it.first, it.second)
            binding?.buttonNext?.isEnabled = (it.first != 0) || (it.second != 0)
        }
    }

    private fun handleCurrentCookingTime(recipe: Resource<RecipeCustom>) {
        if (recipe.status is SuccessStatus) {
            val hours = (recipe.data?.getCookingTime() ?: 0) / TimeUnit.HOURS.toMinutes(1).toInt()
            val minutes = (recipe.data?.getCookingTime() ?: 0) % TimeUnit.HOURS.toMinutes(1).toInt()
            Timber.d("handleCurrentCookingTime: hours = $hours")
            Timber.d("handleCurrentCookingTime: minutes = $minutes")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding?.timePicker?.hour = hours
                binding?.timePicker?.minute = minutes
            } else {
                binding?.timePicker?.currentHour = hours
                binding?.timePicker?.currentMinute = minutes
            }
        }
    }

    private fun nextStep() {
        viewModel.setRecipeCookingTime()
        findNavController().navigate(CreateRecipeTimeFragmentDirections.actionTimeFragmentToIngredientsFragment())
    }
}