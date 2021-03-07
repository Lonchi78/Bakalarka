package com.lonchi.andrej.lonchi_bakalarka.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.SearchBarBinding
import timber.log.Timber

class SearchBar : BaseView<SearchBarBinding> {

    private val noValue: Int = -2
    private var placeholderText: String? = null
    @DrawableRes
    private var startIconDrawableId: Int? = null
    @DrawableRes
    private var endIconDrawableId: Int? = null

    override fun inflateViewBinding(inflater: LayoutInflater): SearchBarBinding =
        SearchBarBinding.inflate(inflater)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setFieldsFromAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        setFieldsFromAttributes(attrs)
    }

    override fun onLayoutInflated() {
        handleLayoutSettings()
    }

    private fun setFieldsFromAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SearchBar,
            0,
            0
        )

        try {
            var resourceId = a.getResourceId(R.styleable.SearchBar_placeholderText, noValue)
            if (resourceId != noValue) placeholderText = context.getString(resourceId)

            // Start Icon Drawable
            resourceId = a.getResourceId(R.styleable.SearchBar_startIconDrawable, noValue)
            if (resourceId != noValue) startIconDrawableId = resourceId
        } finally {
            a.recycle()
        }
    }

    fun handleLayoutSettings() {
        binding?.dactylSearchPlaceholder?.text = placeholderText
        startIconDrawableId?.let { binding?.dactylSearchIcon?.setImageResource(it) }
    }

    fun setPlaceholderText(text: String?) {
        placeholderText = text
        binding?.dactylSearchPlaceholder?.text = text
    }

    fun getPlaceholderText(): String = binding?.dactylSearchPlaceholder?.text.toString()

    fun setStartIconDrawable(drawableId: Int?) {
        this.startIconDrawableId = drawableId
        startIconDrawableId?.let { binding?.dactylSearchIcon?.setImageResource(it) }
    }

    fun setOnClickListener(action: () -> Unit) {
        Timber.d("setOnClickListener: setting")
        binding?.searchContainer?.setOnClickListener {
            Timber.d("setOnClickListener: oon click")
            action()
        }
    }
}
