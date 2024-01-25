package com.gayathri.videplayercompose.di

import android.app.Application
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.gayathri.ktor_client.remote.ApiImpl
import com.gayathri.videplayercompose.IPlayListProvider
import com.gayathri.videplayercompose.PlayListProvider
import com.gayathri.videplayercompose.data.MetaDataReader
import com.gayathri.videplayercompose.data.MetaDataReaderImpl
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.repository.MovieRepository
import com.gayathri.videplayercompose.media.IMediaSourceFactoryProvider
import com.gayathri.videplayercompose.media.IMediaSourceProvider
import com.gayathri.videplayercompose.media.MediaSourceFactoryProvider
import com.gayathri.videplayercompose.media.MediaSourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {
    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(
        app: Application,
        @ApplicationContext context: Context,
        mediaSourceFactoryProvider: IMediaSourceFactoryProvider
    ): ExoPlayer {
        return ExoPlayer.Builder(app)
//            .setMediaSourceFactory(
//                DefaultMediaSourceFactory(context).setDataSourceFactory(mediaSourceProvider.getCacheDataSource())
//            )
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideMetaDataReader(app: Application): MetaDataReader {
        return MetaDataReaderImpl(app)
    }

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(apiImpl: ApiImpl): MovieRepository {
        return MovieRepository(apiImpl)
    }

    /*@Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }*/

    /*@Provides
    @ViewModelScoped
    fun provideVideoDatabase(context: Context): VideoDatabase {
        return VideoDatabase.getInstance(context)
    }*/

}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideVideoDatabase(@ApplicationContext appContext: Context): VideoDatabase {
        return VideoDatabase.getInstance(appContext)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class MediaSourceModule {
    @Provides
    @Singleton
    fun providesMediaSourceFactoryProvider(@ApplicationContext context: Context): IMediaSourceFactoryProvider {
        return MediaSourceFactoryProvider(context)
    }

    @Provides
    @Singleton
    fun providesMediaSourceProvider(mediaSourceFactoryProvider: IMediaSourceFactoryProvider): IMediaSourceProvider {
        return MediaSourceProvider(mediaSourceFactoryProvider)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class MediaDataModule {
    @Provides
    @Singleton
    fun providesPlayListProvider(
        mediaSourceProvider: IMediaSourceProvider,
        videoDatabase: VideoDatabase
    ): IPlayListProvider {
        return PlayListProvider(videoDatabase, mediaSourceProvider)
    }
}