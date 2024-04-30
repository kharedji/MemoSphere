package com.kharedji.memosphere.presentation.utils

import androidx.compose.runtime.mutableStateOf
import com.kharedji.memosphere.domain.models.user.User

object User {
    var user = mutableStateOf(User())
}