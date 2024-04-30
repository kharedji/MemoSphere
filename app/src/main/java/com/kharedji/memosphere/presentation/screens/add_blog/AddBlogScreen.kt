package com.kharedji.memosphere.presentation.screens.add_blog

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kharedji.memosphere.BuildConfig
import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.screens.add_blog.view_model.AddBlogViewModel
import com.kharedji.memosphere.presentation.utils.ActivityUtils.createImageFile
import com.kharedji.memosphere.presentation.utils.ActivityUtils.findActivity
import com.kharedji.memosphere.presentation.utils.User
import java.util.Objects

@Composable
fun AddBlogScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController? = null,
    blogViewModel: AddBlogViewModel? = null,
) {
    val context = LocalContext.current
    val state = blogViewModel?.blogState?.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    var capturedImageUri by rememberSaveable {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val galleryImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                capturedImageUri = it
                image = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
        }
    val cameraImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
            image = MediaStore.Images.Media.getBitmap(context.contentResolver, capturedImageUri)
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraImageLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ){

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = title,
                maxLines = 1,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Blog Image",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { pickImage(context.findActivity(), galleryImageLauncher) }) {
                    Text("Pick Image")
                }
                Button(onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraImageLauncher.launch(uri)
                    } else {
                        // Request a permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
//                    takePhoto(context.findActivity(), cameraImageLauncher, uri)
                }) {
                    Text("Take Photo")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val currentTimeInMillis = System.currentTimeMillis()
                val blog = Blog(
                    title = title,
                    content = content,
                    username = User.user.value.userName,
                    avatarUrl = User.user.value.avatarUrl,
                    imageUrl = "",
                    timestamp = currentTimeInMillis,
                    uid = User.user.value.uid
                )
                if (image == null) {
                    blogViewModel?.addBlog(blog)
                    return@Button
                }
                blogViewModel?.addImageToFirebase(
                    currentTimeInMillis.toString(),
                    capturedImageUri,
                    blog
                )
            },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Blog")
            }
        }
        if (state?.value?.isLoading == true) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .pointerInput(Unit) {}
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center)
                        .padding(10.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    strokeCap = StrokeCap.Round
                )
            }
        }
        if (state?.value?.isBlogAdded == true) {
            Toast.makeText(context, "Blog Added", Toast.LENGTH_SHORT).show()
            navController?.apply {
                navigateUp()
            }
        }
    }

}

private fun pickImage(
    activity: Activity,
    galleryImageLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    ImagePicker.with(activity = activity)
        .compress(2048)
        .galleryOnly()
        .createIntent {
            galleryImageLauncher.launch("image/*")
        }
}

private fun takePhoto(
    activity: Activity,
    cameraImageLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    uri: Uri
) {
    ImagePicker.with(activity = activity)
        .compress(2048)
        .cameraOnly()
        .createIntent {
            cameraImageLauncher.launch(uri)
        }
}

@Preview(showBackground = true)
@Composable
fun AddBlogScreenPreview() {
    AddBlogScreen()
}
