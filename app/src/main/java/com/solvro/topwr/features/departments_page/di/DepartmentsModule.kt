package com.solvro.topwr.features.departments_page.di
//
//import com.solvro.topwr.data.remote.RemoteDataSource
//import com.solvro.topwr.features.departments_page.data.repository.DepartmentRepositoryImpl
//import com.solvro.topwr.features.departments_page.domain.repository.DepartmentRepository
//import com.solvro.topwr.features.departments_page.domain.use_case.GetDepartments_UseCase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DepartmentsModule {
//
//    @Singleton
//    @Provides
//    fun provideDepartmentRepository(
//        remoteDataSource: RemoteDataSource,
//    ) : DepartmentRepository {
//        return DepartmentRepositoryImpl (
//            remoteDataSource,
//        )
//    }
//
//    @Singleton
//    @Provides
//    fun provideGetDepartmentsUseCase(
//        repository: DepartmentRepository
//    ): GetDepartments_UseCase {
//        return GetDepartments_UseCase(repository)
//    }
//
//}