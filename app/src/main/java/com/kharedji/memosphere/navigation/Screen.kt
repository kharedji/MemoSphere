package com.kharedji.memosphere.navigation

sealed class Screen(val route: String) {
    data object SignUp : Screen("signup")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}