package com.gayathri.videplayercompose.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gayathri.videplayercompose.home.HomeViewModel
import com.gayathri.videplayercompose.home.ObserveLifecycleEvents
import com.gayathri.videplayercompose.home.PostUiActions
import com.gayathri.videplayercompose.ui.Loader
import com.gayathri.videplayercompose.ui.posts.PostList
import com.gayathri.videplayercompose.videoplayer.VideoPlayerActivity

@Composable
fun HomePage() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Black,
                    title = {
                        Text(
                            text = "Videos",
                            style = androidx.compose.material.MaterialTheme.typography.h5,
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                    }
                )
            },
        ) { innerPadding ->
            HomeContainer(innerPadding)
        }
    }
}

@Composable
fun HomeContainer(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    viewModel.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)
    val homeUiState by viewModel.uiState.collectAsState()
    Box(modifier = Modifier.padding(innerPadding)) {
        when (val uiState = homeUiState) {
            is HomeUiState.Loading -> Loader()
            is HomeUiState.Content -> HomeVideoContentList(uiState, viewModel)
            is HomeUiState.Error -> Text(text = uiState.exception.message.toString())
        }
    }
}

@Composable
fun HomeVideoContentList(content: HomeUiState.Content, viewModel: HomeViewModel) {
    val context = LocalContext.current
    PostList(
        videos = content.data,
        scrollPosition = viewModel.scrollIndex,
        onScroll = { viewModel.scrollPosition(it) }
    ) { video ->
        val intent = Intent(context, VideoPlayerActivity::class.java)
        intent.putExtra("videoId", video.id)
        context.startActivity(intent)
    }
}
