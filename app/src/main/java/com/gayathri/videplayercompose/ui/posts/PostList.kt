package com.gayathri.videplayercompose.ui.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.gayathri.ktor_client.model.Video
import com.gayathri.videplayercompose.home.PostUiActions
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlin.random.Random

@OptIn(FlowPreview::class)
@Composable
fun PostList(
    videos: List<Video>,
    actions: PostUiActions,
    modifier: Modifier = Modifier,
    onScroll: (index: Int) -> Unit,
    scrollPosition: Int
) {
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.debounce(500L)
            .collectLatest { index ->
                onScroll(index)
            }
    }
    LazyColumn(state = lazyListState, modifier = modifier) {
        items(videos, key = { it.id }) {
            PostItem(
                video = it,
                isLiked = Random.nextBoolean(),
                actions
            )
        }
    }
}