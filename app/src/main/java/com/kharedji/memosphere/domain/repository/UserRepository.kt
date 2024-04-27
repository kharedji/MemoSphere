package com.kharedji.memosphere.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kharedji.memosphere.data.data_source.user.UserDao
import com.kharedji.memosphere.data.utils.State
import com.kharedji.memosphere.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val auth: FirebaseAuth
    val firestore: FirebaseFirestore

    suspend fun signUpUser(email: String, password: String): Flow<State<AuthResult>>
    suspend fun signInUser(email: String, password: String): Flow<State<AuthResult>>
    suspend fun addUserInfo(user: User)
    suspend fun getUser(): Flow<User>
    suspend fun addUserToFirestore(): CollectionReference
}