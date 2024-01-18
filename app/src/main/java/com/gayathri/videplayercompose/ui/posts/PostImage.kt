package com.gayathri.videplayercompose.ui.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gayathri.ktor_client.AppConstant
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun PostImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onPlayClicked: () -> Unit,
    isPlay: Boolean = true
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = AppConstant.MEDIA_BASE_URL.plus(imageUrl),
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
        )
        IconToggleButton(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            checked = isPlay, onCheckedChange = { onPlayClicked() }) {
            val icon = if (isPlay) FaIcons.PlayCircleRegular else FaIcons.PauseCircleRegular
            val tint = if (isPlay) Color.White else MaterialTheme.colorScheme.onPrimary
            FaIcon(faIcon = icon, tint = tint, size = 35.dp)
        }
    }
}
