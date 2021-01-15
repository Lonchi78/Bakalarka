package com.lonchi.andrej.lonchi_bakalarka.ui.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.lonchi.andrej.lonchi_bakalarka.R

class LonchiSnackbar {

    companion object {

        private fun setSnackbarStyle(context: Context, snackbar: Snackbar) {
            snackbar.view.background = context.getDrawable(R.drawable.bg_snackbar)
            snackbar.view.elevation = 0F

            val snackTextView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            TextViewCompat.setTextAppearance(snackTextView, R.style.TextStyleSnackbarLabel)
            snackTextView.setTextColor(ContextCompat.getColor(context, R.color.snackbarTextColor))

            snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }

        /**
         * Create snackbar that is designed based on application styles
         * @param message showing message
         * @param view represent parent layout
         * @param duration Snackbar.LENGTH_LONG or Snackbar.LENGTH_SHORT
         */
        fun make(context: Context,
                 message: String,
                 view: View?,
                 @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_LONG): Snackbar? {

            val activity: Activity? =
                if (context is Activity) context
                else if (context is Fragment) context.activity
                else null

            val snackbar = Snackbar.make(
                view ?: activity?.findViewById(android.R.id.content) ?: return null,
                message,
                duration
            )
            setSnackbarStyle(context, snackbar)

            return snackbar
        }

        /**
         * Create snackbar that is designed based on application styles
         * @param message showing message
         * @param view represent parent layout
         * @param duration Snackbar.LENGTH_LONG or Snackbar.LENGTH_SHORT
         * @param actionText text for action button
         * @param action when the button is pressed, an action is invoked
         */
        fun make(context: Context,
                 message: String,
                 view: View?,
                 @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_LONG,
                 actionText: String,
                 action: () -> Unit): Snackbar? {
            val snackbar = make(context, message, view, duration)
            snackbar?.setAction(actionText) { action.invoke() }
            return snackbar
        }
    }
}