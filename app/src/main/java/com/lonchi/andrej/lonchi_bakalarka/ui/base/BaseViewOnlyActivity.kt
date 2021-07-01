package com.lonchi.andrej.lonchi_bakalarka.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseViewOnlyActivity : AppCompatActivity() {

    val toolbar: Toolbar? by lazy { null }

    protected val TIMBER_TAG = "${this.javaClass.simpleName}: %s"

    @get:LayoutRes
    protected abstract val layoutId: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowFlags()
        if (layoutId != null) {
            setContentView(layoutId!!)
        }
        handleSavedInstanceState(savedInstanceState)
        initView()
    }

    protected open fun setupToolbar(
            title: Int? = null,
            backEnabled: Boolean = true,
            backArrowResourceOverride: Int? = null
    ) = setupToolbar(title?.let { getString(it) }, backEnabled, backArrowResourceOverride)

    protected open fun setupToolbar(
            title: String? = null,
            backEnabled: Boolean = true,
            backArrowResourceOverride: Int? = null
    ) {
        toolbar?.let { it ->
            setSupportActionBar(it)
            setTitle(title)
            if (backArrowResourceOverride != null) {
                supportActionBar?.setHomeAsUpIndicator(backArrowResourceOverride)
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
            supportActionBar?.setDisplayShowHomeEnabled(backEnabled)
            it.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun setTitle(title: String?) {
        supportActionBar?.title = title ?: ""
    }

    protected open fun initWindowFlags() {
    }

    protected abstract fun initView()

    protected open fun handleSavedInstanceState(savedInstanceState: Bundle?) {}
}