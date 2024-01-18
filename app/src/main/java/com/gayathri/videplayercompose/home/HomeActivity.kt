package com.gayathri.videplayercompose.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.ui.home.HomePage
import com.gayathri.videplayercompose.ui.theme.VidePlayerComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VidePlayerComposeTheme {
                HomePage()
            }
        }
    }
}

data class PostUiActions(
    val onLikeClicked: (videoId: Int, isLiked: Boolean) -> Unit?,
    val onCommentsClicked: (videoId: Int) -> Unit?,
    val onSendClicked: (videoId: Int) -> Unit?,
    val onPlayClicked: ((video: Video) -> Unit?)? = null,
)

@Composable
fun <viewModel : LifecycleObserver> viewModel.ObserveLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@ObserveLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@ObserveLifecycleEvents)
        }
    }
}