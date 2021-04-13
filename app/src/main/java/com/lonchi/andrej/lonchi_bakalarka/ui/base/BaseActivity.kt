package com.lonchi.andrej.lonchi_bakalarka.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseActivity<T, V : ViewBinding> : DaggerAppCompatActivity() where T : BaseViewModel {

    protected val TIMBER_TAG = "${this.javaClass.simpleName}: %s"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: T
    protected abstract val vmClassToken: Class<T>
    protected var errorDialog: MaterialDialog? = null
    protected var progressDialog: MaterialDialog? = null

    protected abstract fun initView()
    protected abstract fun bindViewModel()
    protected open fun initDataBinding() {}
    protected open fun handleSavedInstanceState(savedInstanceState: Bundle?) {}
    private fun createViewModel() = ViewModelProviders.of(this, viewModelFactory).get(this.vmClassToken)

    val toolbar: Toolbar? by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val snackBarRoot: View? by lazy { findViewById<View>(R.id.snackbarRoot) ?: findViewById(android.R.id.content) }

    private var _binding: ViewBinding? = null
    protected abstract val bindingInflater: (LayoutInflater) -> V

    @Suppress("UNCHECKED_CAST")
    protected val binding: V
        get() = _binding as V

    /**
     * Init window flags, Set content view, Create viewModel
     * Init data binding, Handle saved instance state,
     * Init view, Bind observers for liveData
     * @param savedInstanceState bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWindowFlags()
        _binding = bindingInflater.invoke(layoutInflater)

        setContentView(requireNotNull(_binding).root)

        viewModel = createViewModel()

        initDataBinding()

        handleSavedInstanceState(savedInstanceState)

        initView()
        bindViewModel()
    }

    /**
     * Clear bindings
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Hide progress dialogs onPause callback
     */
    override fun onPause() {
        super.onPause()
        errorDialog?.dismiss()
        progressDialog?.dismiss()
    }

    /**
     * Set toolbar menu actions
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Setting screen orientation and status bar background
     */
    protected open fun initWindowFlags() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    /**
     * Setting Toolbar with specific parameters
     * @param toolbar Toolbar view
     * @param title
     * @param backArrowResourceOverride resource id for left Toolbar icon
     * @param backEnabled enabled back button
     * @param statusBarBackColor Toolbar background color
     */
    protected open fun setupToolbar(
            toolbar: Toolbar? = this.toolbar,
            title: String? = null,
            backArrowResourceOverride: Int? = null,
            backEnabled: Boolean = true,
            statusBarBackColor: Int? = null) {
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.title = title ?: ""
            supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
            supportActionBar?.setDisplayShowHomeEnabled(backEnabled)
            backArrowResourceOverride?.let { icon -> supportActionBar?.setHomeAsUpIndicator(icon) }
            it.setNavigationOnClickListener { onBackPressed() }
        }

        window.statusBarColor = ContextCompat.getColor(this, statusBarBackColor ?: R.color.colorAccent)
    }

    /**
     * Show no connection custom error snackbar
     */
    protected open fun onNoConnection() {
        //showErrorSnackbar(getString(R.string.error_no_network_connection))
    }

    /**
     * Handle base errors and show snackbar with message
     * @param error error identification that represents specific error code
     * @return true if method handle error, false if it isn't implemented
     */
    protected open fun handleBaseError(error: ErrorIdentification): Boolean =
            when (error) {
                is ErrorIdentification.None -> true
                is ErrorIdentification.Connection -> {
                    onNoConnection()
                    true
                }
                is ErrorIdentification.NotFound,
                is ErrorIdentification.InternalServerError -> {
                    //showErrorSnackbar(error)
                    true
                }
                else -> false
            }

    /**
     * Get error message based on error identification
     * @param errorIdentification represent specific error
     * @return error message
     */
    protected open fun handleErrorStatusMessage(errorIdentification: ErrorIdentification): String {
        return when (errorIdentification) {
            is ErrorIdentification.Authentication -> getString(R.string.error_authentication)
            is ErrorIdentification.Validation -> getString(R.string.error_validation)
            is ErrorIdentification.Connection -> getString(R.string.error_no_network_connection)
            is ErrorIdentification.BadEmailOrPassword -> getString(R.string.error_bad_email_or_password)
            else -> getString(R.string.error_unknown_try_later)
        }
    }

    fun showSnackbar(text: String) {
        Snackbar.make(
            snackBarRoot ?: findViewById(android.R.id.content),
            text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun showSnackbar(stringResource: Int) {
        Snackbar.make(
            snackBarRoot ?: findViewById(android.R.id.content),
            getString(stringResource),
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    fun showProgressDialog(inProgress: Boolean, text: String? = null) {
        if (inProgress) {
            if (progressDialog == null) createProgressDialog(text)
            progressDialog?.show()
        } else {
            progressDialog?.dismiss()
        }
    }

    private fun createProgressDialog(message: String? = null) {
        progressDialog =  MaterialDialog(this).show {
            customView(R.layout.progress_dialog)
            cornerRadius(res = R.dimen.corner_radius_dialog)
            cancelOnTouchOutside(false)
            cancelable(false)
            view.setBackgroundColor(Color.TRANSPARENT)

            val messageText = view.findViewById<TextView>(R.id.title)
            if (message.isNullOrEmpty()) {
                messageText.setVisible(false)
            } else {
                messageText.setVisible(true)
                messageText.text = message
            }
        }
    }
}