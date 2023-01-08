package com.solvro.topwr.features.map.domain.usecase

import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.features.map.domain.MapsRepository
import javax.inject.Inject

class GetBuildingsSearchHistoryUseCase @Inject constructor(
    private val mapsRepository: MapsRepository
) : BaseUseCase<Unit, List<Int>>() {

    override suspend fun action(params: Unit): List<Int> {
        return mapsRepository.getBuildingsSearchHistory()
    }
}