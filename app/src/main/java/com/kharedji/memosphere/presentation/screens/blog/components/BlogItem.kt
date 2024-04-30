package com.kharedji.memosphere.presentation.screens.blog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.kharedji.memosphere.R
import com.kharedji.memosphere.domain.models.blog.Blog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BlogItem(blog: Blog, onLike: () -> Unit ) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 16.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            GlideImage(
                model = blog.avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                loading = placeholder(resourceId = R.drawable.ic_avatar),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = blog.username
                )
                Text(
                    text = SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.ENGLISH
                    ).format(Date(blog.timestamp))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onLike() }) {
                    Icon(
                        imageVector = if(blog.likedByCurrentUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "like",
                        tint = Color.Red,
                        )
                }
                Text(
                    text = blog.likes.toString(),
                    textAlign = TextAlign.Center
                )

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = blog.title,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (blog.imageUrl.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            GlideImage(
                model = blog.imageUrl,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .size(200.dp),
                loading = placeholder(resourceId = R.drawable.ic_launcher),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = blog.content,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlogItemPreview() {
    BlogItem(
        Blog(
            postId = "PostId",
            title = "Title",
            content = "Content",
            username = "Username",
            avatarUrl = "AvatarUrl",
            imageUrl = "ImageUrl",
            timestamp = 1714308200166,
            likes = 0,
            uid = "Uid"
        )
    ){}
}