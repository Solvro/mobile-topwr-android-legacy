package com.solvro.topwr.core.api.model.department

import com.squareup.moshi.Json

data class ProviderMetadataRemote(
    @Json(name = "public_id") val public_id: String?,
    @Json(name = "resource_type") val resource_type: String?
)