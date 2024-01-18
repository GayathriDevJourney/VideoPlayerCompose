package com.gayathri.videplayercompose.ui.video

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.home.PostUiActions
import com.gayathri.videplayercompose.ui.posts.PostInteractionBar

@Composable
fun VideoBottomView(video: Video) {
    Spacer(modifier = Modifier.height(8.dp))
    /*IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Default.FileOpen,
            contentDescription = "Select Video",
            tint = Color.White
        )
    }*/
    PostInteractionBar(video)
    Text(
        text = video.title,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
        color = Color.White
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = video.description,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
        color = Color.White
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = video.subtitle,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
        color = Color.White
    )
    Spacer(modifier = Modifier.height(16.dp))
}