package com.lonchi.andrej.lonchi_skeleton.ui.splash

import com.lonchi.andrej.lonchi_skeleton.ui.base.BaseViewOnlyActivity
import com.lonchi.andrej.lonchi_skeleton.ui.main.MainActivity


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