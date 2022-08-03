package com.solvro.topwr.data.model.departments

import java.io.Serializable

data class InfoSection(
    val id: Int?,
    val info: List<Info>?,
    val name: String?
): Serializable