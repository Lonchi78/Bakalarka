package com.lonchi.andrej.lonchi_bakalarka.ui.home

import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int = R.layout.fragment_home
    override val vmClassToken: Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        btnPicture.setOnClickListener {
            startActivity(CameraActivity.getStartIntent(requireContext()))
        }
    }

    override fun bindViewModel() = Unit

}