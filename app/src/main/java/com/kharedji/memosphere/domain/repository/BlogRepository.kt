package com.kharedji.memosphere.domain.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

interface BlogRepository {
    val firestore: FirebaseFirestore
    val storageRef: FirebaseStorage

    fun addBlog(): CollectionReference
}