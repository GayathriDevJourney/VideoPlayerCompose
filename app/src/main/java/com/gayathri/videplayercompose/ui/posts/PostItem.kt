package com.gayathri.videplayercompose.ui.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.home.PostUiActions

@Composable
fun PostItem(
    video: Video,
    isLiked: Boolean,
    actions: PostUiActions,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        PostImage(
            imageUrl = video.thumb,
            contentDescription = video.description,
            modifier = Modifier.padding(top = 2.dp, bottom = 65.dp),
            onPlayClicked = {
                actions.onPlayClicked?.invoke(video)
            }
        )
        PostBottomView(
            video,
            modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PostBottomView(video: Video, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 8.dp, top = 12.dp)
    ) {
        println("video_player_log PostBottomView compose")
        PostInteractionBar(video)
        Text(
            text = video.title,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = 8.dp, top = 2.dp),
            color = Color.White
        )
        Text(
            text = video.subtitle,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            color = Color.White
        )
        Text(
            text = "12h ago",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            color = Color.White
        )
    }
}