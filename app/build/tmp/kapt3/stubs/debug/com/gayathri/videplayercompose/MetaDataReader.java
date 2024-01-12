package com.gayathri.videplayercompose;

import android.app.Application;
import android.net.Uri;
import android.provider.MediaStore;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/gayathri/videplayercompose/MetaDataReader;", "", "getMetaDataFromUri", "Lcom/gayathri/videplayercompose/MetaData;", "contentUri", "Landroid/net/Uri;", "app_debug"})
public abstract interface MetaDataReader {
    
    @org.jetbrains.annotations.Nullable
    public abstract com.gayathri.videplayercompose.MetaData getMetaDataFromUri(@org.jetbrains.annotations.NotNull
    android.net.Uri contentUri);
}