package com.kharedji.memosphere.presentation.screens.profile

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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kharedji.memosphere.BuildConfig
import com.kharedji.memosphere.R
import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.presentation.screens.profile.view_model.ProfileViewModel
import com.kharedji.memosphere.presentation.utils.ActivityUtils.createImageFile
import com.kharedji.memosphere.presentation.utils.ActivityUtils.findActivity
import com.kharedji.memosphere.presentation.utils.User
import java.util.Objects

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController? = null,
    viewModel: ProfileViewModel? = null
) {
    val context = LocalContext.current
    val uiState = viewModel?.uiState?.collectAsState()
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
//                image = Bitmap.createScaledBitmap(
//                    MediaStore.Images.Media.getBitmap(context.contentResolver, capturedImageUri),
//                    200,
//                    200,
//                    false
//                )
            }
        }
    val cameraImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
//            image = Bitmap.createScaledBitmap(
//                MediaStore.Images.Media.getBitmap(context.contentResolver, capturedImageUri),
//                200,
//                200,
//                false
//            )
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
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = if (capturedImageUri != Uri.EMPTY) capturedImageUri else User.user.value.avatarUrl,
                contentDescription = "avatar",
                loading = placeholder(resourceId = R.drawable.ic_avatar),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = User.user.value.userName.ifEmpty { "UserName" },
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = User.user.value.email.ifEmpty { "johndoe@gmail.com" },
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(20.dp))
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
                viewModel?.uploadAvatar(capturedImageUri)
            }) {
                Text("Upload Avatar")
            }
        }
        if (uiState?.value?.isLoading == true) {
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

    }
}

private fun pickImage(
    activity: Activity,
    galleryImageLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    ImagePicker.with(activity = activity)
        .compress(1024)
        .galleryOnly()
        .createIntent {
            galleryImageLauncher.launch("image/*")
        }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}