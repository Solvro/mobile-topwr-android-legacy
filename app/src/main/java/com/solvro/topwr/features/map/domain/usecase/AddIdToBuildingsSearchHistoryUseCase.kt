package com.solvro.topwr.features.map.domain.usecase

import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.features.map.domain.MapsRepository
import javax.inject.Inject

class AddIdToBuildingsSearchHistoryUseCase @Inject constructor(
    private val mapsRepository: MapsRepository
) : BaseUseCase<Int, Unit>(){

    override suspend fun action(params: Int) {
        mapsRepository.addIdToBuildingsSearchHistory(params)
    }
}