package com.gayathri.videplayercompose.di

import android.app.Application
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.gayathri.videplayercompose.data.MetaDataReader
import com.gayathri.videplayercompose.data.MetaDataReaderImpl
import com.gayathri.videplayercompose.data.local.VideoDatabase
import com.gayathri.videplayercompose.data.repository.MovieRepository
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
    fun provideVideoPlayer(app: Application): ExoPlayer {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @ViewModelScoped
    fun provideMetaDataReader(app: Application): MetaDataReader {
        return MetaDataReaderImpl(app)
    }

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(): MovieRepository {
        return MovieRepository()
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