package com.kharedji.memosphere.domain.models.blog

data class Blog(
    val title: String,
    val content: String,
    val username: String,
    val avatarUrl : String,
    val imageUrl: String,
    val timestamp: Long,
    val uid: String,
) {
    constructor(): this("","","","","",0,"")
}

