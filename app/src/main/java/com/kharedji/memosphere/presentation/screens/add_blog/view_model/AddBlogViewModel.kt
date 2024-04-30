package com.kharedji.memosphere.presentation.screens.add_blog.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.domain.use_case.blogs.BlogUseCases
import com.kharedji.memosphere.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddBlogViewModel @Inject constructor(
    private val blogUseCases: BlogUseCases
): ViewModel() {
    private val _blogState = MutableStateFlow(AddBlogState())
    val blogState = _blogState.asStateFlow()

    fun addImageToFirebase(imageName: String, uri: Uri, blog: Blog) {
        blogUseCases.addImageToFirbase(imageName, uri) {
            when(it) {
                is Resource.Loading -> {
                    _blogState.value = AddBlogState(isLoading = true)
                }
                is Resource.Success -> {
                    addBlog(blog.copy(imageUrl = it.data ?: ""))
                }
                is Resource.Error -> {
                    _blogState.value = AddBlogState(isLoading = false, error = it.message ?: "An error occurred")
                }
            }
        }
    }

    fun addBlog(blog: Blog) {
        blogUseCases.addBlog(blog) {
            when(it) {
                is Resource.Loading -> {
                    _blogState.value = AddBlogState(isLoading = true)
                }
                is Resource.Success -> {
                    _blogState.value = AddBlogState(isLoading = false, isBlogAdded = true)
                }
                is Resource.Error -> {
                    _blogState.value = AddBlogState(isLoading = false, error = it.message ?: "An error occurred")
                }
            }
        }
    }
}