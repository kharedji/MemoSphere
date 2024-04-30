package com.kharedji.memosphere.presentation.screens.blog

import androidx.compose.foundation.layout.Arrangement
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
        val blogList = listOf(
            Blog(
                title = "Title",
                username = "John Doe",
                avatarUrl = "https://randomuser.me/api/portraits",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                imageUrl = "https://picsum.photos/200/300",
                timestamp = System.currentTimeMillis(),
                uid = "1"
            ),
            Blog(
                title = "Title",
                username = "John Doe",
                avatarUrl = "https://randomuser.me/api/portraits",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                imageUrl = "https://picsum.photos/200/300",
                timestamp = System.currentTimeMillis(),
                uid = "2"
            ),
            Blog(
                title = "Title",
                username = "John Doe",
                avatarUrl = "https://randomuser.me/api/portraits",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                imageUrl = "https://picsum.photos/200/300",
                timestamp = System.currentTimeMillis(),
                uid = "3"
            ),

        )
        if (blogsState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .padding(16.dp)
            )
        }
        if (blogsState.value.blogs.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn {
                    items(blogsState.value.blogs) { blog ->
                        BlogItem(blog = blog)
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BlogsScreenPreview() {
    BlogsScreen()
}