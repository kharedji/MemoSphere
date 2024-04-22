package com.kharedji.memosphere.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kharedji.memosphere.presentation.screens.main.MainScreen
import com.kharedji.memosphere.presentation.screens.signin.SignInScreen
import com.kharedji.memosphere.presentation.screens.signin.view_models.SignInViewModel
import com.kharedji.memosphere.presentation.screens.signup.SignUpScreen
import com.kharedji.memosphere.presentation.screens.signup.view_models.SignUpViewModel

@Composable
fun Navigation(
    padding: PaddingValues = PaddingValues(),
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
        composable(route = Screen.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                paddingValues = padding,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.SignIn.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            SignInScreen(
                paddingValues = padding,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screen.Main.route){
            MainScreen(
                paddingValues = padding,
                navController = navController
            )
        }
    }
}