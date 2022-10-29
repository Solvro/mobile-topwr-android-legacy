package com.solvro.topwr.core.api.model.departments

import android.os.Parcelable
import com.solvro.topwr.features.departments.domain.model.InfoSection
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class InfoSectionRemote(
    val id: Int?,
    val info: List<InfoRemote>?,
    val name: String?
): Serializable, Parcelable {
    fun toDomain() = InfoSection(
        id, info?.map { it.toDomain() }, name
    )
}