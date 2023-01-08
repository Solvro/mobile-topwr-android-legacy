package com.solvro.topwr.features.map.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Building (
    val address: String?,
    val code: String?,
    val id: Int?,
    val name: String?,
    val photoUrl: String?,
    val longitude: Double?,
    val latitude: Double?,
): Parcelable