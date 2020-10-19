package com.lonchi.andrej.lonchi_bakalarka.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    protected val TIMBER_TAG = "${this.javaClass.simpleName}: %s"

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}