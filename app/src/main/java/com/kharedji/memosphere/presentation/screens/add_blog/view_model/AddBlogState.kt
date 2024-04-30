package com.kharedji.memosphere.presentation.screens.add_blog.view_model

data class AddBlogState(
    val isLoading : Boolean = false,
    val error : String = "",
    val isBlogAdded : Boolean = false
)
