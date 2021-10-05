package com.solvro.topwr.data.model.building

data class BuildingListItem(
    val id: String,
    val name: String,
    val departmentCode: String,
    val coords: Pair<Float, Float>
)