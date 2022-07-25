package com.solvro.topwr.data.model.common

import java.io.Serializable

data class Formats(
    val large: Large?,
    val medium: Medium?,
    val small: Small?,
    val thumbnail: Thumbnail?
): Serializable