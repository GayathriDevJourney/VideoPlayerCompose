package com.gayathri.videplayercompose.home

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gayathri.ktor_client.model.Video
import com.gayathri.ktor_client.network.DataState
import com.gayathri.videplayercompose.data.local.LikesEntity
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.local.mapToEntity
import com.gayathri.videplayercompose.data.local.mapToUiModelList
import com.gayathri.videplayercompose.data.repository.MovieRepository
import com.gayathri.videplayercompose.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val videoDatabase: VideoDatabase,
) : ViewModel(), DefaultLifecycleObserver {

    var scrollIndex: Int = 0
    private val repo: MovieRepository = MovieRepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMovies()
        }
    }

    private suspend fun getFromLocalDatabase(): List<Video> {
        return videoDatabase.videoDao().getVideos().mapToUiModelList()
    }

    private suspend fun getMovies() {
        repo.getMovies().collect { dataState ->
            _uiState.update {
                when (dataState) {
                    is DataState.Loading -> HomeUiState.Loading
                    is DataState.Success -> {
                        videoDatabase.videoDao().insertAll(dataState.data.mapToEntity())
                        HomeUiState.Content(dataState.data)
                    }

                    is DataState.Error -> {
                        println("video_player_log ${getFromLocalDatabase().size} ${getFromLocalDatabase().isNotEmpty()}")
                        if (getFromLocalDatabase().isNotEmpty()) HomeUiState.Content(
                            getFromLocalDatabase()
                        )
                        else HomeUiState.Error(dataState.exception)
                    }
                }
            }
        }
    }

    fun postLike(videoId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            if (isLiked) {
                videoDatabase.likesDao().insert(LikesEntity(videoId))
            } else {
                videoDatabase.likesDao().delete(videoId = videoId)
            }
        }
    }

    fun getLike(videoId: Int) {
        viewModelScope.launch {
            videoDatabase.likesDao().getLikeStatus(videoId)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "onCreate")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d(TAG, "onResume")
        viewModelScope.launch {
            getMovies()
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "onStart")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d(TAG, "onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "onDestroy")
    }

    fun scrollPosition(index: Int) {
        scrollIndex = index
    }
}

const val TAG = "video_player_log"