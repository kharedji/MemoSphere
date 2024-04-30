package com.kharedji.memosphere.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kharedji.memosphere.domain.repository.BlogRepository

class BlogRepositoryImpl: BlogRepository {

    override val firestore: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    override val storageRef: FirebaseStorage
        get() = FirebaseStorage.getInstance()

    /**
     * Add blog to the Firestore database
     * @return CollectionReference
     */
    override fun addBlog(): CollectionReference {
        return firestore.collection("blogs")
    }
}