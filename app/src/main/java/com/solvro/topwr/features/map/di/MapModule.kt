package com.solvro.topwr.features.map.di

import com.solvro.topwr.features.map.data.MapsRepositoryImpl
import com.solvro.topwr.features.map.domain.MapsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {

    companion object;

    @Binds
    @Singleton
    abstract fun provideMapsRepository(
        mapsRepository: MapsRepositoryImpl
    ): MapsRepository

}