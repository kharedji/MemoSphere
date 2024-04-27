package com.kharedji.memosphere.domain.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName : String,
    val email : String,
    val avatarUrl : String,
    val uid: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
){
    constructor(): this("","","","")
}
