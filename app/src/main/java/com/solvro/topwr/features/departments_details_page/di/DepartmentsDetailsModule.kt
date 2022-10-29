package com.solvro.topwr.features.departments_details_page.di
//
//import com.solvro.topwr.data.remote.RemoteDataSource
//import com.solvro.topwr.features.departments_details_page.data.repository.DepartmentDetailsRepositoryImpl
//import com.solvro.topwr.features.departments_details_page.domain.repository.DepartmentDetailsRepository
//import com.solvro.topwr.features.departments_details_page.domain.use_case.GetScienceClubs_UseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DepartmentsDetailsModule {
//
//    @Singleton
//    @Provides
//    fun provideDepartmentDetailsRepository(
//        remoteDateSource: RemoteDataSource
//    ) : DepartmentDetailsRepository {
//        return DepartmentDetailsRepositoryImpl(
//            remoteDateSource
//        )
//    }
//
//    @Singleton
//    @Provides
//    fun provideGetScienceClubs(
//        repository: DepartmentDetailsRepository
//    ): GetScienceClubs_UseCase {
//        return GetScienceClubs_UseCase(
//            repository
//        )
//    }
//}