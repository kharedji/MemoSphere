package com.kharedji.memosphere.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

object ActivityUtils {
    fun getClassName(activity: Any): String {
        return activity::class.java.simpleName
    }

    internal fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("Permissions should be called in the context of an Activity")
    }
}