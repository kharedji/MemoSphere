package com.kharedji.memosphere.domain.use_case.user

import android.net.Uri
import com.kharedji.memosphere.domain.repository.UserRepository
import com.kharedji.memosphere.presentation.utils.User
import com.kharedji.memosphere.state.Resource

class AddAvatarToFirebase(
    val repository: UserRepository
) {

    operator fun invoke(uri: Uri, callBack: (Resource<String>) -> Unit) {
        callBack(Resource.Loading())
        val storageRef = repository.storageRef.reference
        val imageRef = storageRef.child("avatars/${User.user.value.uid}")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.continueWithTask {
            imageRef.downloadUrl
        }.addOnCompleteListener{
            if (it.isSuccessful) {
                val downloadUri = it.result
                downloadUri?.let {
                    callBack(Resource.Success(it.toString()))
                }
            }
        }.addOnFailureListener {
            callBack(Resource.Error(it.message.toString()))
        }
    }
}