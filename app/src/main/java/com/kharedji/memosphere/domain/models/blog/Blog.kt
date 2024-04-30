package com.kharedji.memosphere.domain.models.blog

data class Blog(
    val postId: String = "",
    val title: String,
    val content: String,
    val username: String,
    val avatarUrl : String,
    val imageUrl: String,
    val timestamp: Long,
    val likes: Int = 0,
    val uid: String,
    val likedByCurrentUser: Boolean = false
) {
    constructor(): this("","","","","","",0,0,"",false)
}

