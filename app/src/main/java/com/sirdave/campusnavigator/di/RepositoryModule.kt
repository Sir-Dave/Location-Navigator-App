package com.sirdave.campusnavigator.di

import android.content.Context
import com.sirdave.campusnavigator.data.OSMRepository
import com.sirdave.campusnavigator.data.PlaceRepositoryImpl
import com.sirdave.campusnavigator.data.remote.Api
import com.sirdave.campusnavigator.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlaceRepository(
        @ApplicationContext context: Context,
        api: Api
    ): PlaceRepository {
        return PlaceRepositoryImpl(context, api)
    }

    @Provides
    @Singleton
    fun provideOSMRepository(@ApplicationContext context: Context): OSMRepository {
        return OSMRepository(context)
    }
}