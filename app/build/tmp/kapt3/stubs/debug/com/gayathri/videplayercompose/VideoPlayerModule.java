package com.gayathri.videplayercompose;

import android.app.Application;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\t"}, d2 = {"Lcom/gayathri/videplayercompose/VideoPlayerModule;", "", "()V", "provideMetaDataReader", "Lcom/gayathri/videplayercompose/MetaDataReader;", "app", "Landroid/app/Application;", "provideVideoPlayer", "Landroidx/media3/common/Player;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.android.components.ViewModelComponent.class})
public final class VideoPlayerModule {
    @org.jetbrains.annotations.NotNull
    public static final com.gayathri.videplayercompose.VideoPlayerModule INSTANCE = null;
    
    private VideoPlayerModule() {
        super();
    }
    
    @dagger.Provides
    @dagger.hilt.android.scopes.ViewModelScoped
    @org.jetbrains.annotations.NotNull
    public final androidx.media3.common.Player provideVideoPlayer(@org.jetbrains.annotations.NotNull
    android.app.Application app) {
        return null;
    }
    
    @dagger.Provides
    @dagger.hilt.android.scopes.ViewModelScoped
    @org.jetbrains.annotations.NotNull
    public final com.gayathri.videplayercompose.MetaDataReader provideMetaDataReader(@org.jetbrains.annotations.NotNull
    android.app.Application app) {
        return null;
    }
}