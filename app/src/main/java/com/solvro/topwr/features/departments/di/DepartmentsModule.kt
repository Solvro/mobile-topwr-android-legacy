package com.solvro.topwr.features.departments.di

import com.solvro.topwr.features.departments.data.DepartmentsRepositoryImpl
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DepartmentsModule {

    companion object

    @Singleton
    @Binds
    abstract fun provideDepartmentsRepository(
        departmentsRepository: DepartmentsRepositoryImpl
    ): DepartmentsRepository

}