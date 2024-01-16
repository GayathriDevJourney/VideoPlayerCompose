package com.gayathri.videplayercompose.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gayathri.videplayercompose.VideoPlayerApp
import com.gayathri.videplayercompose.ui.theme.VidePlayerComposeTheme
import com.gayathri.videplayercompose.ui.video.VideoPlayerPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VidePlayerComposeTheme {
                VideoPlayerPage(intent.extras)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VidePlayerComposeTheme {
        VideoPlayerApp()
    }
}