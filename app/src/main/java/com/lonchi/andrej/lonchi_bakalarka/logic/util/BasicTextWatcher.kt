package com.lonchi.andrej.lonchi_bakalarka.logic.util

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.functions.Consumer

/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
class BasicTextWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Consumer(onTextChanged).accept(text.toString())
    }
}