package com.lonchi.andrej.lonchi_bakalarka.ui.discover

import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseFragment
import com.lonchi.andrej.lonchi_bakalarka.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class DiscoverFragment : BaseFragment<DiscoverViewModel>() {
    companion object {
        fun newInstance() = DiscoverFragment()
    }

    override val layoutId: Int = R.layout.fragment_discover
    override val vmClassToken: Class<DiscoverViewModel> = DiscoverViewModel::class.java

    override fun initView() {
        text.setOnClickListener {
            startActivity(CameraActivity.getStartIntent(requireContext()))
        }
    }

    override fun bindViewModel() = Unit
}