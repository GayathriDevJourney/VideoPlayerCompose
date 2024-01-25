package com.gayathri.download_media_service.di

import android.content.Context
import com.gayathri.download_media_service.DownloadRequestBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MediaDownloadServiceModule {
    @Provides
    @Singleton
    fun provideDownloadRequestBuilder(@ApplicationContext context: Context): DownloadRequestBuilder {
        return DownloadRequestBuilder(context)
    }
}