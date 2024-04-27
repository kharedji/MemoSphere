package com.kharedji.memosphere.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.File

fun File.asImageBitmap(): ImageBitmap? {
    return try {
        val bitmap = BitmapFactory.decodeFile(this.absolutePath)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}