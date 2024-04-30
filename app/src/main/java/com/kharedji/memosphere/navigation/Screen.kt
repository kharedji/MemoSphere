package com.kharedji.memosphere.navigation

sealed class Screen(val route: String) {
    data object SignUp : Screen("signup")
    data object SignIn : Screen("signin")
    data object Main : Screen("main")
    data object Notes : Screen("notes")
    data object Blogs : Screen("blogs")
    data object AddBlog : Screen("add_blog")
    data object AddEditNote : Screen("add_edit_note")
    data object Profile : Screen("profile")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}