package com.solvro.topwr.features.map.data

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.data.local.DataStoreSource
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.map.domain.MapsRepository
import com.solvro.topwr.features.map.domain.model.Building
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreSource: DataStoreSource
) : MapsRepository {

    override suspend fun getBuildings(): Resource<List<Building>> = remoteDataSource.getBuildings()
        .map { listOfBuildings -> listOfBuildings.map { building -> building.toDomain() } }

    override fun addIdToBuildingsSearchHistory(id: Int) {
        dataStoreSource.addToBuildingsSearchHistory(id)
    }

    override fun getBuildingsSearchHistory() = dataStoreSource.getBuildingsSearchHistory()

}