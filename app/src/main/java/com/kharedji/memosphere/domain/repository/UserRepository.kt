package com.kharedji.memosphere.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kharedji.memosphere.data.utils.State
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val auth: FirebaseAuth

    suspend fun signUpUser(email: String, password: String): Flow<State<AuthResult>>
    suspend fun signInUser(email: String, password: String): Flow<State<AuthResult>>
  
}