package com.gayathri.videplayercompose.ui.video.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerControllerState
import com.gayathri.videplayercompose.ui.video.custom.state.PlayerState

@Composable
fun VideoLayout(
    state: PlayerControllerState,
    action: (VideoPlayerControlAction) -> Unit
) {
    with(state) {
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200))
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PlayerIcon(icon = Icons.Rounded.FastRewind) {
                        action(VideoPlayerControlAction.OnRewind)
                    }
                    when (playerState) {
                        PlayerState.BUFFERING -> CircularProgressIndicator()
                        else -> PlayerIcon(
                            icon = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayCircle
                        ) {
                            action(if (isPlaying) VideoPlayerControlAction.OnPause else VideoPlayerControlAction.OnPlay)
                        }
                    }

                    PlayerIcon(icon = Icons.Rounded.FastForward) {
                        action(VideoPlayerControlAction.OnForward)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerIcon(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .size(35.dp)
            .clickable { onClick() }
    )
}
