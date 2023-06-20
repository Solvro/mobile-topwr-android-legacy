package com.solvro.topwr.features.faq.di

import com.solvro.topwr.features.faq.data.FaqRepositoryImpl
import com.solvro.topwr.features.faq.domain.FaqRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class FaqModule {
    companion object

    @Singleton
    @Binds
    abstract fun provideFaqRepository(
        faqRepository: FaqRepositoryImpl
    ): FaqRepository
}