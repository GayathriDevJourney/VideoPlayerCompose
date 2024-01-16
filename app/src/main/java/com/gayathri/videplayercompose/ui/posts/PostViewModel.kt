package com.gayathri.videplayercompose.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gayathri.videplayercompose.data.local.LikesEntity
import com.gayathri.videplayercompose.data.local.VideoDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val videoDatabase: VideoDatabase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(false)
    val uiState: StateFlow<Boolean> = _uiState.asStateFlow()

    fun postLike(videoId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            if (isLiked) {
                videoDatabase.likesDao().insert(LikesEntity(videoId))
            } else {
                videoDatabase.likesDao().delete(videoId)
            }
            _uiState.update { isLiked }
        }
    }

    fun getLikeStatus(videoId: Int): Boolean {
        var isLiked = false
        viewModelScope.launch {
            isLiked = videoDatabase.likesDao().getLikeStatus(videoId) != null
            _uiState.update { isLiked }
        }
        return isLiked
    }

    fun onCommentsClicked() {

    }

    fun onSendClicked() {

    }
}
