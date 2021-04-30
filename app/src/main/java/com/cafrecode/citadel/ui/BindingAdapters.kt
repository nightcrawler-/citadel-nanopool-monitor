package com.cafrecode.citadel.ui

import android.view.View
import androidx.databinding.BindingAdapter

class BindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("app:visibleGone")
        fun visibleGone(view: View, visible: Boolean) {
            view.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}