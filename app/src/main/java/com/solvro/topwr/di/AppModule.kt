package com.solvro.topwr.di

import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.data.remote.ToPwrService
import com.solvro.topwr.data.repository.MainRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // baseUrl for Retrofit
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "https://to-pwr-backend.herokuapp.com/"

    //provide retrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        client: OkHttpClient,
        @Named("BaseUrl") baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideMoshi():Moshi = Moshi.Builder().build()

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    //provide toPwrService
    @Provides
    fun provideToPwrService(retrofit: Retrofit): ToPwrService = retrofit.create(ToPwrService::class.java)


    @Provides
    fun provideRemoteDataSource(service: ToPwrService): RemoteDataSource = RemoteDataSource(service)

    //provides instance of MainRepository
    @Provides
    fun provideMainRepository(remoteDataSource: RemoteDataSource): MainRepository = MainRepository(remoteDataSource)
}