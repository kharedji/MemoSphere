package com.kharedji.memosphere.presentation.screens.blog.view_model

import com.kharedji.memosphere.domain.models.blog.Blog

data class BlogsState(
    val isLoading: Boolean = false,
    val blogs: List<Blog> = mutableListOf(),
    val error: String = ""
)
