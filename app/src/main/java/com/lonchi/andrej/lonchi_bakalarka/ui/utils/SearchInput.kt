package com.lonchi.andrej.lonchi_bakalarka.ui.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.core.widget.addTextChangedListener
import com.lonchi.andrej.lonchi_bakalarka.R
import com.lonchi.andrej.lonchi_bakalarka.databinding.SearchInputBinding
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setSelectionEnd
import com.lonchi.andrej.lonchi_bakalarka.logic.util.setVisible
import kotlinx.coroutines.*


class SearchInput : BaseView<SearchInputBinding> {

    private val noValue: Int = -2
    private var placeholderText: String? = null
    @DrawableRes private var startIconDrawableId: Int? = null
    @DrawableRes private var endIconDrawableId: Int? = null

    override fun inflateViewBinding(inflater: LayoutInflater): SearchInputBinding =
        SearchInputBinding.inflate(inflater)

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
        setupClearIcon()
    }

    private fun setFieldsFromAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SearchInput,
            0,
            0
        )

        try {
            // Start Icon Drawable
            var resourceId = a.getResourceId(R.styleable.SearchInput_startIconDrawable, noValue)
            if (resourceId != noValue) startIconDrawableId = resourceId

            // End Icon Drawable
            resourceId = a.getResourceId(R.styleable.SearchInput_endIconDrawable, noValue)
            if (resourceId != noValue) endIconDrawableId = resourceId else endIconDrawableId = R.drawable.ic_close_20

        } finally {
            a.recycle()
        }
    }

    private fun handleLayoutSettings() {
        binding?.searchEndIcon?.setImageResource(R.drawable.ic_close_20)
    }

    fun setPlaceholderText(text: String?) {
        placeholderText = text
        binding?.searchPlaceholder?.hint = text
    }

    fun getInputField() = binding?.searchPlaceholder

    fun getInputText(): String = binding?.searchPlaceholder?.text?.toString().orEmpty()

    fun setEndIconDrawable(drawableId: Int?) {
        this.endIconDrawableId = drawableId
        endIconDrawableId?.let { binding?.searchEndIcon?.setImageResource(it) }
    }

    private fun setEndIconVisible(visible: Boolean) {
        binding?.searchEndIcon?.setVisible(visible)
    }

    fun setEndIconClickClearInput(value: Boolean) {
        binding?.searchEndIcon?.isClickable = true
        binding?.searchEndIcon?.isFocusable = true
        binding?.searchEndIcon?.setOnClickListener {
            if (value) binding?.searchPlaceholder?.text?.clear()
        }
    }

    fun setEndIconOnClickListener(action: () -> Unit) {
        binding?.searchEndIcon?.isClickable = true
        binding?.searchEndIcon?.isFocusable = true
        binding?.searchEndIcon?.setOnClickListener {
            binding?.searchPlaceholder?.text?.clear()
            action()
        }
    }

    fun setMicrophoneIconOnClickListener(action: () -> Unit) {
        binding?.iconMic?.isClickable = true
        binding?.iconMic?.isFocusable = true
        binding?.iconMic?.setOnClickListener { action() }
    }

    fun setTextObserver(action: (String) -> Unit, delayMillis: Long = 200L) {
        binding?.searchPlaceholder?.afterTextChangedDebounce(delayMillis) { action.invoke(it) }
    }

    private fun setupClearIcon() {
        setEndIconOnClickListener { binding?.searchPlaceholder?.setText("") }
        binding?.searchPlaceholder?.addTextChangedListener { setEndIconVisible(!it.isNullOrEmpty()) }
        setEndIconVisible(!binding?.searchPlaceholder?.text.isNullOrEmpty())
    }

    fun changeCurrentQuery(input: String) {
        binding?.searchPlaceholder?.setText(input)
        binding?.searchPlaceholder?.setSelectionEnd()
    }

    fun EditText.afterTextChangedDebounce(delayMillis: Long, input: (String) -> Unit) {
        var lastInput = ""
        var debounceJob: Job? = null
        val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable != null) {
                    val newtInput = editable.toString()
                    debounceJob?.cancel()
                    if (lastInput != newtInput) {
                        lastInput = newtInput
                        debounceJob = uiScope.launch {
                            delay(delayMillis)
                            if (lastInput == newtInput) {
                                input(newtInput)
                            }
                        }
                    }
                }
            }

            override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}