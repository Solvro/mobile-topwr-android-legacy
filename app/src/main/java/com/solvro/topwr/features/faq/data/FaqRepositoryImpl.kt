package com.solvro.topwr.features.faq.data

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.faq.domain.model.AboutUs
import com.solvro.topwr.features.faq.domain.model.Info
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.faq.domain.FaqRepository
import javax.inject.Inject

class FaqRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : FaqRepository {
    override suspend fun getInfos(nameFilter: String): Resource<List<Info>> {
        return when (val resource = remoteDataSource.getInfos(nameFilter)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> Resource.Success(resource.data)
            is Resource.Loading -> Resource.Loading()
        }
    }

    override suspend fun getAboutUs(): Resource<AboutUs> {
        return when (val resource = remoteDataSource.getAboutUs()) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> Resource.Success(resource.data)
            is Resource.Loading -> Resource.Loading()
        }
    }


}