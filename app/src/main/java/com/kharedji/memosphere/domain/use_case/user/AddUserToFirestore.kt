package com.kharedji.memosphere.domain.use_case.user

import com.kharedji.memosphere.domain.models.user.User
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.state.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AddUserToFirestore(
    private val repository: UserRepository
) {

    suspend operator fun invoke(user: User, callBack: (Resource<Boolean>) -> Unit) {
        callBack(Resource.Loading())
        repository.addUserToFirestore()
            .firestore.collection("users")
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                callBack(Resource.Success(true))
            }
            .addOnFailureListener {
                callBack(Resource.Error(it.message.toString()))
            }

    }
}