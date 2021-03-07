package com.lonchi.andrej.lonchi_bakalarka.ui.utils

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding


abstract class BaseView<V : ViewBinding> : FrameLayout {
    protected var inflater: LayoutInflater

    protected var binding: V? = null
        private set

    protected abstract fun inflateViewBinding(inflater: LayoutInflater): V

    constructor(context: Context) : super(context) {
        inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        onFinishInflate()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        setViewBinding()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        setViewBinding()
    }

    private fun setViewBinding() {
        binding = inflateViewBinding(inflater)
        addView((binding as ViewBinding).root)
    }

    /**
     * Views can be binded (findViewById) in this method
     */
    protected abstract fun onLayoutInflated()

    final override fun onFinishInflate() {
        super.onFinishInflate()
        if (!isInEditMode) {
            onLayoutInflated()
        }
    }

    protected fun getLifecycleOwner(): LifecycleOwner {
        var context: Context = context
        while (context !is LifecycleOwner) {
            context = (context as ContextWrapper).baseContext
        }
        return context
    }
}
