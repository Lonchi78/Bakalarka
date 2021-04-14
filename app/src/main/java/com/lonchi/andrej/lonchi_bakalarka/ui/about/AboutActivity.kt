package com.lonchi.andrej.lonchi_bakalarka.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.ActivityAboutBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openSendEmail
import com.lonchi.andrej.lonchi_bakalarka.logic.util.openWebUrl
import com.lonchi.andrej.lonchi_bakalarka.ui.base.BaseActivity


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class AboutActivity : BaseActivity<AboutViewModel, ActivityAboutBinding>() {

    companion object {
        fun getStartIntent(context: Context, extras: Bundle? = null): Intent =
            Intent(context, AboutActivity::class.java)
                .apply { if (extras != null) this.putExtras(extras) }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityAboutBinding = { ActivityAboutBinding.inflate(it) }
    override val vmClassToken: Class<AboutViewModel> = AboutViewModel::class.java

    override fun initView() {
        binding.iconBack.setOnClickListener { onBackPressed() }
        binding.labelDataSourceValue.setOnClickListener {
            openWebUrl(getString(R.string.about_data_source_url))
        }
        binding.labelDiplomaThesisValue.setOnClickListener {
            openWebUrl(getString(R.string.about_diploma_thesis_url))
        }
        binding.labelAuthorValue.setOnClickListener {
            openWebUrl(getString(R.string.about_author_url))
        }
        binding.labelContactValue.setOnClickListener {
            openSendEmail(
                recipient = getString(R.string.about_contact_email),
                subject = "",
                body = "",
            )
        }
    }

    override fun bindViewModel() = Unit
}