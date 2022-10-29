package com.solvro.topwr.core.api.model.department

data class DepartmentRemote(
    val id: String,
    val name: String,
    val code: String,
    val majors: List<String>,
    val address: String,
    val websiteUrl: String,
    val socialMediaUrl: String,
    val immageUrl: String
)
