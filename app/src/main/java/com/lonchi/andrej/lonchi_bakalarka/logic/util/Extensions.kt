package com.lonchi.andrej.lonchi_bakalarka.logic.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.FileProvider
import androidx.core.graphics.TypefaceCompatUtil
import com.lonchi.andrej.lonchi_bakalarka.BuildConfig
import com.lonchi.andrej.lonchi_bakalarka.R


/**
 * @author Andrej Lončík <andrejloncik@gmail.com>
 */
/* * * * * * * * * * * * * * * VISIBILITY * * * * * * * * * * * * * * * * */

fun gone(vararg views: View) {
    views.forEach { it.visibility = View.GONE }
}

fun visible(vararg views: View) {
    views.forEach { it.visibility = View.VISIBLE }
}

fun invisible(vararg views: View) {
    views.forEach { it.visibility = View.INVISIBLE }
}

fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun View.setVisible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else this.visibility = View.GONE
}

fun Array<View>.setVisible(visible: Boolean) {
    this.forEach { it.setVisible(visible) }
}

/* * * * * * * * * * * * * * * ANIMATION * * * * * * * * * * * * * * * * */

fun View.animateExitLeft(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_to_left)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            visibility = View.INVISIBLE
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
    this.startAnimation(anim)
}

fun View.animateExitRight(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_to_right)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            visibility = View.INVISIBLE
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
    this.startAnimation(anim)
}

fun View.animateExitTop(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_to_top)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            visibility = View.INVISIBLE
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
    this.startAnimation(anim)
}

fun View.animateEnterRight(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_from_right)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {}

        override fun onAnimationStart(p0: Animation?) {
            visibility = View.VISIBLE
        }
    })
    this.startAnimation(anim)
}

fun View.animateEnterLeft(context: Context) {

    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_from_left)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {}

        override fun onAnimationStart(p0: Animation?) {
            visibility = View.VISIBLE
        }
    })
    this.startAnimation(anim)
}

fun View.animateEnterTop(context: Context) {

    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_from_top)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {}

        override fun onAnimationStart(p0: Animation?) {
            visibility = View.VISIBLE
        }
    })
    this.startAnimation(anim)
}

fun View.animateFadeIn(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.fade_in)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            this@animateFadeIn.setVisible(true)
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
    this.startAnimation(anim)
}

/**
 * @param endVisibility GONE or INVISIBLE?
 */
fun View.animateFadeOut(context: Context, endVisibility: Int) {
    val anim = AnimationUtils.loadAnimation(context, R.anim.fade_out)

    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            this@animateFadeOut.visibility = endVisibility
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
    this.startAnimation(anim)
}


/* * * * * * * * * * * * * * * INTENTS * * * * * * * * * * * * * * * * * */

fun Context.openWebUrl(url: String) {
    val formattedUrl = if (url.startsWith("http")) url else "http://$url"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(formattedUrl)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openPhoneCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openSendSMS(phoneNumber: String = "", message: String = "") {
    val uri = Uri.parse("smsto:$phoneNumber")
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra("sms_body", message)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openSendEmail(recipient: String = "", subject: String = "", body: String = "") {
    val uriText = "mailto:$recipient" + "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body)
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = (Uri.parse(uriText))
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openMap(query: String, uriBegin: String) {
    val encodedQuery = Uri.encode(query)
    val uri = Uri.parse("$uriBegin?q=$encodedQuery&z=16")
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = uri
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.copyToClipboard(data: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(getString(R.string.app_name), data)
    clipboard.setPrimaryClip(clip)
}

@SuppressLint("RestrictedApi")
fun Context.getTempFileUri(): Uri? {
    return FileProvider.getUriForFile(this,
            this.applicationContext.packageName + ".provider",
            TypefaceCompatUtil.getTempFile(this) ?: return null)
}


/* * * * * * * * * * * * * * * EDIT TEXT * * * * * * * * * * * * * * * * */

fun Activity.hideKeyboard() {
    if (this.currentFocus != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
    }
}

fun Activity.openKeyboard(editText: EditText) {
    editText.post {
        editText.requestFocus()
        editText.setSelectionEnd()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun EditText.afterTextChange(action: (content: String) -> Unit): TextWatcher {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = action(p0?.toString().orEmpty())
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    }
    this.addTextChangedListener(watcher)
    return watcher
}

fun EditText.setSelectionEnd() {
    this.setSelection(this.text.length)
}

/* * * * * * * * * * * * * * * Sensor * * * * * * * * * * * * * * * * */

fun Activity.isGpsOn(): Boolean =
        (this.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(LocationManager.GPS_PROVIDER)


/* * * * * * * * * * * * * Others * * * * * * * * * * * *  */

fun getAppVersion(): String = BuildConfig.VERSION_NAME
