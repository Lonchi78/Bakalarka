package com.lonchi.andrej.lonchi_bakalarka.ui.splash

import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseViewOnlyActivity
import com.lonchi.andrej.lonchi_bakalarka.ui.main.MainActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class SplashActivity : BaseViewOnlyActivity() {
    override val layoutId: Int? = null

    override fun initView() {
        startActivity(MainActivity.getStartIntent(this, intent.extras))
        finish()
    }
}