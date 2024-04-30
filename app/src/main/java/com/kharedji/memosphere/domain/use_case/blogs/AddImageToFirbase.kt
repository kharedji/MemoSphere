package com.kharedji.memosphere.domain.use_case.blogs

import android.net.Uri
import com.kharedji.memosphere.domain.repository.BlogRepository
import com.kharedji.memosphere.presentation.utils.User
import com.kharedji.memosphere.state.Resource

class AddImageToFirbase(
    val repository: BlogRepository
) {

    operator fun invoke(imageName: String, uri: Uri, callBack: (Resource<String>) -> Unit) {
        callBack(Resource.Loading())
        val storageRef = repository.storageRef.reference
        val imageRef = storageRef.child("photos/${User.user.value.uid}/$imageName")
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