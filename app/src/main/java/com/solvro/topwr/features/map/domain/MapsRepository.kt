package com.solvro.topwr.features.map.domain

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.map.domain.model.Building

interface MapsRepository {
    suspend fun getBuildings(): Resource<List<Building>>
    fun addIdToBuildingsSearchHistory(id: Int)
    fun getBuildingsSearchHistory(): List<Int>
}