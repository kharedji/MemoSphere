package com.kharedji.memosphere.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kharedji.memosphere.presentation.screens.signup.SignUpScreen
import com.kharedji.memosphere.presentation.screens.signup.view_models.SignUpViewModel

@Composable
fun Navigation(
    padding: PaddingValues = PaddingValues(),
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.SignUp.route) {
        composable(route = Screen.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(paddingValues = padding,navController = navController, viewModel = viewModel)
        }
    }
}