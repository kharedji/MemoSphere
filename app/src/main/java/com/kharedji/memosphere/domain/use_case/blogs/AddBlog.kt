package com.kharedji.memosphere.domain.use_case.blogs

import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.domain.repository.BlogRepository
import com.kharedji.memosphere.state.Resource

class AddBlog(
    val repository: BlogRepository
) {

        operator fun invoke(blog:Blog, callBack: (Resource<Boolean>) -> Unit) {
            val postId = repository.addBlog().document().id
            val newBlog = blog.copy(postId = postId)
            repository.addBlog().document(postId).set(newBlog)
                .addOnSuccessListener {
                    callBack(Resource.Success(true))
                }
                .addOnFailureListener {
                    callBack(Resource.Error(it.message ?: "An error occurred"))
                }
        }
}