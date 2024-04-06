package com.kharedji.memosphere.presentation.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.screens.signup.view_models.SignUpViewModel

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController,
    viewModel: SignUpViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    val localFocusManager = LocalFocusManager.current

    // non-null data in the uiState means that the sign up request
    // was processed successfully and the account has been created.
    // Therefore the NavController can route to the MainScreen.
    uiState.data?.let {
        navController.apply {
            popBackStack()
        }
    }

    Column(
        Modifier.padding(paddingValues = paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email")
            },
            value = email,
            placeholder = {
                Text(text = "Email")
            },
            onValueChange = {
                email = it
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = {
                Text(text = "password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email")
            },
            value = password,
            placeholder = {
                Text(text = "Password")
            },
            onValueChange = {
                password = it
            }
        )

        Button(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(email = email, password = password)
            }
        }) {
            Text(text = "Sign Up")
        }

        if (uiState.loading) {
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
        if (uiState.error?.isNotEmpty() == true){
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = uiState.error!!,
                color = Color.Red
            )
        }
    }

}