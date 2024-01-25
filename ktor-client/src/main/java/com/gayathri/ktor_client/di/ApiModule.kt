package com.gayathri.ktor_client.di

import com.gayathri.ktor_client.remote.ApiImpl
import com.gayathri.ktor_client.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideAPIInterface(): ApiInterface {
        return ApiImpl()
    }
}
