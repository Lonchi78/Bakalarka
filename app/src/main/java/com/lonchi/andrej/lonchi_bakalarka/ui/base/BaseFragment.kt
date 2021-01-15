package com.lonchi.andrej.lonchi_bakalarka.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.data.utils.ErrorIdentification
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import com.lonchi.andrej.lonchi_bakalarka.ui.utils.LonchiSnackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseFragment<T, V : ViewBinding> : DaggerFragment() where T : BaseViewModel {

    protected val TIMBER_TAG = "${this.javaClass.simpleName}: %s"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: T
    protected abstract val vmClassToken: Class<T>
    protected var errorDialog: MaterialDialog? = null
    protected var progressDialog: MaterialDialog? = null

    protected abstract fun initView()
    protected abstract fun bindViewModel()
    protected fun createViewModel() = ViewModelProviders.of(this, viewModelFactory).get(this.vmClassToken)

    @get:LayoutRes
    protected abstract val layoutId: Int

    private val snackBarRoot: View? by lazy { view?.findViewById<View>(R.id.snackbarRoot) }

    protected var binding: V? = null
    protected abstract val bindingInflater: (View) -> V


    /**
     * Create viewModel on onCreate
     * @param savedInstanceState bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
    }

    /**
     * Inflate view on onCreateView
     * @param inflater layout inflater
     * @param container view group
     * @param savedInstanceState bundle
     * @return inflated view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    /**
     * Init view, bind observers for liveData
     * @param view created view
     * @param savedInstanceState bundle
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = bindingInflater.invoke(view)
        initView()
        bindViewModel()
    }

    /**
     * clear bindings
     * @param view created view
     * @param savedInstanceState bundle
     */
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    /**
     * Hide progress dialogs onPause callback
     */
    override fun onPause() {
        super.onPause()
        progressDialog?.dismiss()
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
        progressDialog =  MaterialDialog(requireContext()).show {
            customView(R.layout.progress_dialog)
            cornerRadius(res = R.dimen.corner_radius_dialog)
            cancelOnTouchOutside(false)
            cancelable(false)
            view.setBackgroundColor(Color.TRANSPARENT)

            val messageText = view.findViewById<TextView>(R.id.message)
            if (message.isNullOrEmpty()) {
                messageText.setVisible(false)
            } else {
                messageText.setVisible(true)
                messageText.text = message
            }
        }
    }

    /**
     * Show error snackbar that is designed based on application styles
     * @param errorMessage error message
     * @param view represent parent layout
     */
    fun showErrorSnackbar(errorMessage: String, view: View? = null) {
        LonchiSnackbar.make(context ?: return,
            errorMessage,
            view ?: activity?.findViewById(android.R.id.content) ?: return,
            Snackbar.LENGTH_LONG
        )?.show()
    }
}