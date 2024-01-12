package com.gayathri.videplayercompose;

import android.net.Uri;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012J\b\u0010\u0016\u001a\u00020\u0014H\u0014J\u0006\u0010\u0017\u001a\u00020\u0014J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/gayathri/videplayercompose/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "player", "Landroidx/media3/common/Player;", "metaDataReader", "Lcom/gayathri/videplayercompose/MetaDataReader;", "(Landroidx/lifecycle/SavedStateHandle;Landroidx/media3/common/Player;Lcom/gayathri/videplayercompose/MetaDataReader;)V", "getPlayer", "()Landroidx/media3/common/Player;", "videoItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/gayathri/videplayercompose/VideoItem;", "getVideoItems", "()Lkotlinx/coroutines/flow/StateFlow;", "videoUris", "Landroid/net/Uri;", "addVideoUri", "", "uri", "onCleared", "playFromRemote", "playVideo", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.media3.common.Player player = null;
    @org.jetbrains.annotations.NotNull
    private final com.gayathri.videplayercompose.MetaDataReader metaDataReader = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<android.net.Uri>> videoUris = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gayathri.videplayercompose.VideoItem>> videoItems = null;
    
    @javax.inject.Inject
    public MainViewModel(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull
    androidx.media3.common.Player player, @org.jetbrains.annotations.NotNull
    com.gayathri.videplayercompose.MetaDataReader metaDataReader) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.media3.common.Player getPlayer() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.gayathri.videplayercompose.VideoItem>> getVideoItems() {
        return null;
    }
    
    public final void addVideoUri(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
    }
    
    public final void playVideo(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
    }
    
    public final void playFromRemote() {
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
}