package com.kharedji.memosphere.presentation.screens.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kharedji.memosphere.R
import com.kharedji.memosphere.domain.models.user.User
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.screens.signup.components.RegisterOutlinedText
import com.kharedji.memosphere.presentation.screens.signup.view_models.SignUpViewModel

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues = PaddingValues(),
    navController: NavController? = null,
    viewModel: SignUpViewModel? = null
) {
    val context = LocalContext.current
    val uiState = viewModel?.uiState?.collectAsState()

    var userName: String by rememberSaveable {
        mutableStateOf("")
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isEmailValid by rememberSaveable {
        mutableStateOf(false)
    }

    var isPasswordValid by rememberSaveable {
        mutableStateOf(false)
    }

    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }

    val localFocusManager = LocalFocusManager.current

    // non-null data in the uiState means that the sign up request
    // was processed successfully and the account has been created.
    // Therefore the NavController can route to the MainScreen.
    uiState?.value?.data?.let { authResult ->
        authResult.user?.let {
            viewModel.addUserToFirestore(User(
                userName = userName,
                email = email,
                avatarUrl = "",
                uid = it.uid
            ),
                navController = navController,
                context = context
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            RegisterOutlinedText(
                value = userName,
                onValueChanged = {
                    userName = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "username")},
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Enter your full name") }
            )

            RegisterOutlinedText(
                value = email,
                onValueChanged = {
                    email = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "email")
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            RegisterOutlinedText(
                value = password,
                onValueChanged = {
                    password = it
                                 },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_password), contentDescription = "password")
                },
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_eye),
                        contentDescription = "show password",
                        modifier = Modifier.clickable {
                            showPassword = !showPassword
                        }
                    )
                },
                applyVisualTransformation = !showPassword
            )
            /*TextField(
                label = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "password")
                },
                value = password,
                placeholder = {
                    Text(text = "Password")
                },
                onValueChange = {
                    password = it
                }
            )*/

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel?.signUp(email = email, password = password)
                }
            }) {
                Text(text = "Sign Up")
            }

            if (uiState?.value?.error?.isNotEmpty() == true){
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = uiState.value.error?: "error signing up",
                    color = Color.Red
                )
            }
        }

        if (uiState?.value?.loading == true) {
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

@Preview(showBackground = true)
@Composable
fun Preview(){
    SignUpScreen()
}