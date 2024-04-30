package com.kharedji.memosphere.presentation.screens.blog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kharedji.memosphere.domain.models.blog.Blog
import com.kharedji.memosphere.navigation.Screen
import com.kharedji.memosphere.presentation.screens.blog.components.BlogItem
import com.kharedji.memosphere.presentation.screens.blog.view_model.BlogsViewModel

@Composable
fun BlogsScreen(
    navController: NavController? = null
) {
    val blogsViewModel: BlogsViewModel = hiltViewModel()
    val blogsState = blogsViewModel.blogState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        blogsViewModel.getBlogs()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController?.navigate(Screen.AddBlog.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) {

        if (blogsState.value.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(16.dp)
                )
            }
        }
        if (blogsState.value.blogs.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn {
                    items(blogsState.value.blogs) { blog ->
                        BlogItem(blog = blog){
                            // update the blogs state list with the new liked blog
                            val updatedBlogs = blogsState.value.blogs.toMutableList()
                            val blogIndex = updatedBlogs.indexOfFirst { it.postId == blog.postId }
                            if (blogIndex != -1) {
                                updatedBlogs[blogIndex] = blog
                                blogsViewModel.updateBlogsState(updatedBlogs)
                            }
                            blogsViewModel.likeBlog(blog.copy(likedByCurrentUser = !blog.likedByCurrentUser))
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No blogs found")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BlogsScreenPreview() {
    BlogsScreen()
}