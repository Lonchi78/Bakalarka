package com.lonchi.andrej.lonchi_bakalarka.ui.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class CameraActivity : BaseActivity<CameraViewModel>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, CameraActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val layoutId: Int? = R.layout.activity_camera
    override val vmClassToken: Class<CameraViewModel> = CameraViewModel::class.java

    override fun initView() {
    }

    override fun bindViewModel() = Unit
}