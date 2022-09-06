package com.solvro.topwr.data.model.departments

class Color(
    gradientFirst: String?,
    gradientSecond: String?,
    val id: Int?
) {
    val gradientFirst: String? = gradientFirst
        get() = field?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR"
    val gradientSecond: String? = gradientSecond
        get() = field?.let { "#$it" } ?: "#$PLACEHOLDER_COLOR"

    companion object {
        const val PLACEHOLDER_COLOR = "DB2B10"
    }
}