package com.kharedji.memosphere.domain.use_case.blogs

data class BlogUseCases(
    val getBlogs: GetBlogs,
    val addBlog: AddBlog,
    val addImageToFirbase: AddImageToFirbase
)
