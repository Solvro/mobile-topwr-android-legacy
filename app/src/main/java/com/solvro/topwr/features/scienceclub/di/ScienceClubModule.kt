package com.solvro.topwr.features.scienceclub.di

import com.solvro.topwr.features.scienceclub.data.ScienceClubRepositoryImpl
import com.solvro.topwr.features.scienceclub.domain.ScienceClubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScienceClubModule {

    companion object;

    @Binds
    @Singleton
    abstract fun provideScienceClubRepository(scienceClub: ScienceClubRepositoryImpl): ScienceClubRepository

}