package com.solvro.topwr.data.model.notices

import android.os.Parcelable
import com.solvro.topwr.data.model.common.Photo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

@Parcelize
data class Notices(
    val created_at: String?,
    val description: String?,
    val id: Int?,
    val infoSection: @RawValue List<Any>?,
    val link: @RawValue Any?,
    val locale: String?,
    val localizations: @RawValue List<Any>?,
    val photo: Photo?,
    val published_at: String?,
    val title: String?,
    val updated_at: String?
) : Parcelable {
    fun getFormattedTime(): String {
        val inputDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val dateTime = ZonedDateTime.parse(published_at, inputDateTimeFormatter)
        val outputDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return dateTime.format(outputDateTimeFormatter)
    }
}