package com.kharedji.memosphere.presentation.screens.profile.view_model

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val avatarUri : String = "",
)
