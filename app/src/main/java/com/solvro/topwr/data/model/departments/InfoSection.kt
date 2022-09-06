package com.solvro.topwr.data.model.departments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class InfoSection(
    val id: Int?,
    val info: List<Info>?,
    val name: String?
): Serializable, Parcelable