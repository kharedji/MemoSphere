package com.kharedji.memosphere.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kharedji.memosphere.R
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.activities.view_models.MainViewModel
import com.kharedji.memosphere.presentation.utils.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    mainViewModel: MainViewModel? = null
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 15.dp)
                .align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(100.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .align(Alignment.CenterHorizontally),
            ) {
                GlideImage(
                    model = User?.user?.value?.avatarUrl ?: "",
                    contentDescription = "avatar",
                    loading = placeholder(R.drawable.ic_avatar),
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = User?.user?.value?.userName ?: "User",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        navController.navigate(Screen.Profile.route)
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "profile",
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Profile",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .clickable {
                    User.user.value = com.kharedji.memosphere.domain.models.user.User()
                    mainViewModel?.deleteUser()
                    coroutineScope.launch {
                        drawerState.close()
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.ExitToApp,
                contentDescription = "sign out",
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Sign Out",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

    }
}