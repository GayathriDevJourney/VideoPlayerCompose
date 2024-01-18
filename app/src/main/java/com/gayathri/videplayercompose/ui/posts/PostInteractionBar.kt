package com.gayathri.videplayercompose.ui.posts

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.gayathri.ktor_client.AppConstant
import com.gayathri.ktor_client.model.Video
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun PostInteractionBar(
    video: Video,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel<PostViewModel>(key = video.id.toString())
) {
    viewModel.getLikeStatus(video.id)
    val isLiked = viewModel.uiState.collectAsState(false).value
    val context = LocalContext.current
    Row(modifier = modifier) {
        IconToggleButton(
            checked = isLiked,
            onCheckedChange = { viewModel.postLike(video.id, it) }) {
            val icon = if (isLiked) FaIcons.Heart else FaIcons.HeartRegular
            val tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onPrimary
            FaIcon(faIcon = icon, tint = tint)
        }
        IconToggleButton(checked = false, onCheckedChange = { viewModel.onCommentsClicked() }) {
            FaIcon(faIcon = FaIcons.CommentAltRegular, tint = MaterialTheme.colorScheme.onPrimary)
        }
        IconToggleButton(checked = false, onCheckedChange = {
            viewModel.onSendClicked()
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TITLE, video.title)
                putExtra(
                    Intent.EXTRA_TEXT,
                    AppConstant.MEDIA_BASE_URL + video.source
                )
                type = "text/plain"
            }.also {
                startActivity(context, Intent.createChooser(it, null), null)
            }
        }) {
            FaIcon(faIcon = FaIcons.PaperPlaneRegular, tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Preview
@Composable
fun PostInteractionBarPreview() {
    PostInteractionBar(video = Video(0, "", "", "", "", ""))
}