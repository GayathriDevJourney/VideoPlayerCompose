package com.gayathri.videplayercompose.ui.video

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.gayathri.ktor_client.AppConstant
import com.gayathri.videplayercompose.ui.video.custom.DurationController
import com.gayathri.videplayercompose.ui.video.custom.VideoLayout
import com.gayathri.videplayercompose.ui.video.custom.VideoPlayerControlAction
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerOrientation
import com.gayathri.videplayercompose.videoplayer.VideoPlayerViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@UnstableApi
@Composable
fun VideoView(viewModel: VideoPlayerViewModel) {
    val activity = LocalContext.current

    val orientation = viewModel.orientation.collectAsState()

    val screenOrientation = if (orientation.value == PlayerOrientation.Portrait) {
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
    (activity as? Activity)?.requestedOrientation = screenOrientation
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                    (context as? Activity)?.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    it.useController = true // default controller disabled
                    it.setShowSubtitleButton(true)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clickable {
                    viewModel.setControlsVisibility()
                },
            update = {
                Log.d("video_player_log", "exo player update ${it.player}")
            }
        )
        /*VideoLayout(
            viewModel = viewModel
        )
        DurationController(
            viewModel,
            Modifier.align(Alignment.BottomStart),
            onSeekChanged = {
                viewModel.onAction(VideoPlayerControlAction.OnSeekChanged(it))
            })*/
        UpNextEndCard(viewModel = viewModel, Modifier.align(Alignment.BottomStart))
    }
}

@Composable
fun UpNextEndCard(viewModel: VideoPlayerViewModel, modifier: Modifier) {
    val showUpNextCard = viewModel.showUpNextCard.collectAsState()
    println("showUpNextCard ${showUpNextCard.value}")
    if (showUpNextCard.value) {
        Card(
            backgroundColor = Color.White,
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = "Up Next",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(vertical = 1.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 1.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = AppConstant.MEDIA_BASE_URL.plus(viewModel.getUpNextItem()?.thumb),
                            modifier = modifier
                                .size(50.dp)
                                .aspectRatio(8 / 16F),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                        )
                        FaIcon(
                            faIcon = FaIcons.PlayCircleRegular,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "${viewModel.getUpNextItem()?.title}",
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}
