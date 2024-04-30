package com.kharedji.memosphere.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kharedji.memosphere.presentation.screens.add_blog.AddBlogScreen
import com.kharedji.memosphere.presentation.screens.add_blog.view_model.AddBlogViewModel
import com.kharedji.memosphere.presentation.screens.add_edit_note.AddEditNoteScreen
import com.kharedji.memosphere.presentation.screens.add_edit_note.AddEditNoteViewModel
import com.kharedji.memosphere.presentation.screens.main.MainScreen
import com.kharedji.memosphere.presentation.screens.profile.ProfileScreen
import com.kharedji.memosphere.presentation.screens.profile.view_model.ProfileViewModel
import com.kharedji.memosphere.presentation.screens.signin.SignInScreen
import com.kharedji.memosphere.presentation.screens.signin.view_models.SignInViewModel
import com.kharedji.memosphere.presentation.screens.signup.SignUpScreen
import com.kharedji.memosphere.presentation.screens.signup.view_models.SignUpViewModel
import com.kharedji.memosphere.presentation.utils.User


@Composable
fun Navigation(
    padding: PaddingValues = PaddingValues(),
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        // do not remove the null safety operator even if the IDE suggests it
        startDestination = if(User.user?.value?.email?.isNotEmpty() == true) Screen.Main.route else Screen.SignIn.route
    ) {
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

        composable(route = Screen.AddBlog.route){
            val viewModel: AddBlogViewModel = hiltViewModel()
            AddBlogScreen(
                paddingValues = padding,
                navController = navController,
                blogViewModel = viewModel
            )
        }

        composable(route = Screen.AddEditNote.route +
                "?noteId={noteId}&noteColor={noteColor}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColor"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
            ){
            val color = it.arguments?.getInt("noteColor") ?: -1
            val viewModel: AddEditNoteViewModel = hiltViewModel()
            AddEditNoteScreen(
                paddingValues = padding,
                navController = navController,
                noteColor = color,
                viewModel = viewModel
            )
        }

        composable(route = Screen.Profile.route){
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                paddingValues = padding,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}