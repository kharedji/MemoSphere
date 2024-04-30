package com.kharedji.memosphere.presentation.screens.blog.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.domain.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(
    private val blogsRepository: BlogRepository
): ViewModel() {
    private val _blogState = MutableStateFlow(BlogsState())
    val blogState = _blogState.asStateFlow()

    fun getBlogs() {
        viewModelScope.launch {
            _blogState.value = BlogsState(isLoading = true)
            blogsRepository.firestore.collection("blogs")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        _blogState.value = BlogsState(error = error.message ?: "An unknown error occurred")
                    } else {
                        val blogs = value?.toObjects(Blog::class.java)
                        _blogState.value = BlogsState(blogs = blogs ?: emptyList())
                    }
                }
        }
    }
}