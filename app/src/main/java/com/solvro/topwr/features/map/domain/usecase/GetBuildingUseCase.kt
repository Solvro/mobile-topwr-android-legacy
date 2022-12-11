package com.solvro.topwr.features.map.domain.usecase

import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.map.domain.MapsRepository
import com.solvro.topwr.features.map.domain.model.Building
import javax.inject.Inject

class GetBuildingUseCase @Inject constructor(
    private val mapsRepository: MapsRepository
) : BaseUseCase<Unit?, Resource<List<Building>>>() {

    override suspend fun action(params: Unit?): Resource<List<Building>> {
        return mapsRepository.getBuildings()
    }
}