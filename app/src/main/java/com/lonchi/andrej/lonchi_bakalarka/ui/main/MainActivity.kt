package com.lonchi.andrej.lonchi_bakalarka.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityMainBinding
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.onboarding.OnboardingActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, MainActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = { ActivityMainBinding.inflate(it) }
    override val vmClassToken: Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        val navController = findNavController(R.id.mainNavHostFragment)
        binding.mainBottomNavigationView.itemIconTintList = null
        NavigationUI.setupWithNavController(binding.mainBottomNavigationView, navController)
    }

    override fun bindViewModel() {
        handleFirstStart()
    }

    private fun handleFirstStart() {
        if (viewModel.getFirstUse()) {
            viewModel.updateFirstStart(false)
            binding.mainBottomNavigationView.postDelayed({
                startActivity(OnboardingActivity.getStartIntent(this))
            }, 1000)
        }
    }
}