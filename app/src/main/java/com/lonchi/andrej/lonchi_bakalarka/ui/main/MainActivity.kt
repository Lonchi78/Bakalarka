package com.lonchi.andrej.lonchi_bakalarka.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class MainActivity : BaseActivity<MainViewModel>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, MainActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val layoutId: Int? = R.layout.activity_main
    override val vmClassToken: Class<MainViewModel> = MainViewModel::class.java

    override fun initView() {
        val navController = findNavController(R.id.mainNavHostFragment)
        mainBottomNavigationView?.setupWithNavController(navController)
        mainBottomNavigationView?.itemIconTintList = null
    }

    override fun bindViewModel() = Unit

    override fun onPause() {
        super.onPause()
        viewModel.resetState()
    }
}