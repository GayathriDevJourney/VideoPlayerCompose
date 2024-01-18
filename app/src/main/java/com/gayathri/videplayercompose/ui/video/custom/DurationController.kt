package com.gayathri.videplayercompose.ui.video.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gayathri.videplayercompose.utils.formatMinSec

@Composable
fun DurationController(
    modifier: Modifier = Modifier,
    playerProgressBarDataModel: PlayerProgressBarDataModel,
    onSeekChanged: (timeMs: Float) -> Unit,
    onExpand: () -> Unit
) {

    with(playerProgressBarDataModel) {
        Column(modifier = modifier) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // buffer bar
                Slider(
                    modifier = Modifier.height(25.dp),
                    value = bufferedPercentage.toFloat(),
                    enabled = false,
                    onValueChange = { /*do nothing*/ },
                    valueRange = 0f..100f,
                    colors =
                    SliderDefaults.colors(
                        disabledThumbColor = Color.Transparent,
                        disabledActiveTrackColor = Color.Gray
                    )
                )

                // seek bar
                Slider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp),
                    value = currentTime.toFloat(),
                    onValueChange = onSeekChanged,
                    valueRange = 0f..totalDuration.toFloat(),
                    colors =
                    SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTickColor = Color.White
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "${currentTime.formatMinSec()} / ${totalDuration.formatMinSec()}",
                    color = Color.White
                )
                IconButton(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp),
                    onClick = onExpand
                ) {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = androidx.media3.ui.R.drawable.exo_ic_fullscreen_enter),
                        contentDescription = "Enter/Exit fullscreen"
                    )
                }
            }
        }
    }
}