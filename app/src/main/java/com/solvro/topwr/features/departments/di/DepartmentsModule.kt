package com.solvro.topwr.features.departments.di

import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments.data.DepartmentsRepositoryImpl
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.use_case.GetDepartments_UseCase
import com.solvro.topwr.features.departments.domain.use_case.GetScienceClubs_UseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DepartmentsModule {

    @Singleton
    @Provides
    fun provideDepartmentsRepository(
        remoteDataSource: RemoteDataSource
    ): DepartmentsRepository {
        return DepartmentsRepositoryImpl(
            remoteDataSource
        )
    }

    @Singleton
    @Provides
    fun provideGetScienceClubs(
        repository: DepartmentsRepository
    ): GetScienceClubs_UseCase {
        return GetScienceClubs_UseCase(
            repository
        )
    }

    @Singleton
    @Provides
    fun provideGetDepartmentsUseCase(
        repository: DepartmentsRepository
    ): GetDepartments_UseCase {
        return GetDepartments_UseCase(repository)
    }

}