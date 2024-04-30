package com.kharedji.memosphere.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kharedji.memosphere.data.data_source.user.UserDao
import com.kharedji.memosphere.data.utils.State
import com.kharedji.memosphere.domain.models.user.User
import com.kharedji.memosphere.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override val auth = Firebase.auth
    override val firestore: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    override val storageRef: FirebaseStorage
        get() = FirebaseStorage.getInstance()

    /**
     * Sign up user with email and password
     * @param email
     * @param password
     * @return Flow<State<AuthResult>>
     */
    override suspend fun signUpUser(email: String, password: String) = flow {
        emit(State.loading())
        auth.createUserWithEmailAndPassword(email, password).await().run {
            emit(State.success(this))
        }
    }.catch {
        emit(State.error(it.message ?: UNKNOWN_ERROR))
    }

    /**
     * Sign in user with email and password
     * @param email
     * @param password
     * @return Flow<State<AuthResult>>
     */
    override suspend fun signInUser(email: String, password: String): Flow<State<AuthResult>> {
        return flow {
            emit(State.loading())
            auth.signInWithEmailAndPassword(email, password).await().run {
                emit(State.success(this))
            }
        }.catch {
            emit(State.error(it.message ?: UNKNOWN_ERROR))
        }
    }

    /**
     * Add user information to the database
     * @param user
     */
    override suspend fun addUserInfo(user: User) {
        userDao.insertUser(user)
    }

    /**
     * Delete user information from the database
     * @param user
     */
    override suspend fun deleteUser() {
        userDao.deleteUser()
    }

    /**
     * Get user information from the database
     * @return Flow<User>
     */
    override suspend fun getUser(): Flow<User> {
        return userDao.getUser()
    }

    /**
     * Add user to Firestore
     * @return CollectionReference
     */
    override suspend fun addUserToFirestore(): CollectionReference {
           return firestore.collection("users")
    }

    companion object {
        const val UNKNOWN_ERROR = "An unknown error occurred. Please try again later."
    }
}