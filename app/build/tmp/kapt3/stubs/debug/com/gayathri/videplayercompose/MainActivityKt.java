package com.gayathri.videplayercompose;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.material.icons.Icons;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.tooling.preview.Preview;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.media3.ui.PlayerView;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\b\u0010\u0006\u001a\u00020\u0001H\u0007\u001a\u0010\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0007\u001a\u0018\u0010\n\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0007\u00a8\u0006\u000e"}, d2 = {"Greeting", "", "name", "", "modifier", "Landroidx/compose/ui/Modifier;", "GreetingPreview", "VideoBottomView", "viewModel", "Lcom/gayathri/videplayercompose/MainViewModel;", "VideoContainer", "lifecycle", "Landroidx/lifecycle/Lifecycle$Event;", "VideoView", "app_debug"})
public final class MainActivityKt {
    
    @androidx.compose.runtime.Composable
    public static final void VideoContainer(@org.jetbrains.annotations.NotNull
    com.gayathri.videplayercompose.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.Lifecycle.Event lifecycle) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VideoBottomView(@org.jetbrains.annotations.NotNull
    com.gayathri.videplayercompose.MainViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void VideoView(@org.jetbrains.annotations.NotNull
    com.gayathri.videplayercompose.MainViewModel viewModel, @org.jetbrains.annotations.NotNull
    androidx.lifecycle.Lifecycle.Event lifecycle) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void Greeting(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void GreetingPreview() {
    }
}