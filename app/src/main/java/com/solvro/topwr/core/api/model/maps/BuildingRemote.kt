package com.solvro.topwr.core.api.model.maps

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import com.solvro.topwr.features.map.domain.model.Building
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BuildingRemote (
    val addres: String?,
    val code: String?,
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: @RawValue List<Any>?,
    val latitude: Double?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val longitude: Double?,
    val name: String?,
    val photo: Photo?,
    val published_at: String?,
    val updated_at: String?
): Parcelable {

    fun toDomain() = Building(
        addres, code, id, name, photo?.url, longitude, latitude
    )

}