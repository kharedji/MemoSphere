package com.kharedji.memosphere.presentation.screens.add_blog

import android.app.Activity
import android.app.Activity.RESULT_OK
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kharedji.memosphere.presentation.screens.add_blog.view_model.AddBlogViewModel
import com.kharedji.memosphere.presentation.utils.ActivityUtils.findActivity
import com.kharedji.memosphere.presentation.utils.asImageBitmap
import java.io.File

@Composable
fun AddBlogScreen(
    blogViewModel: AddBlogViewModel? = null,
) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf<File?>(null) }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content Description") },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        image?.let {
            Image(bitmap = it.asImageBitmap()!!,
                contentDescription = "Blog Image",
                modifier = Modifier.size(200.dp))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { pickImage(context.findActivity()) }) {
                Text("Pick Image")
            }
            Button(onClick = { takePhoto(context.findActivity()) }) {
                Text("Take Photo")
            }
        }
    }
}

private fun pickImage(activity: Activity) {
    ImagePicker.with(activity = activity)
        .galleryOnly()
        .start(111)
}

private fun takePhoto(activity: Activity) {
    ImagePicker.with(activity = activity)
        .cameraOnly()
        .start(222)
}

@Preview(showBackground = true)
@Composable
fun AddBlogScreenPreview() {
    AddBlogScreen()
}
