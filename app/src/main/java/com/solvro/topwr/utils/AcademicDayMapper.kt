package com.solvro.topwr.utils

import android.content.Context
import com.solvro.topwr.R
import com.solvro.topwr.data.model.date.Date
import java.util.*

class AcademicDayMapper {
    companion object {
        fun mapAcademicScheduleDay(context: Context, date: Date?): String {
            return with(context) {
                when (date?.day) {
                    Calendar.SUNDAY -> {
                        if (date.even)
                            "${getString(R.string.Even_f)} ${getString(R.string.Sunday)}"
                        else
                            "${getString(R.string.Odd_f)} ${getString(R.string.Sunday)}"
                    }
                    Calendar.MONDAY -> {
                        if (date.even)
                            "${getString(R.string.Even)} ${getString(R.string.Monday)}"
                        else
                            "${getString(R.string.Odd)} ${getString(R.string.Monday)}"
                    }
                    Calendar.TUESDAY -> {
                        if (date.even)
                            "${getString(R.string.Even)} ${getString(R.string.Tuesday)}"
                        else
                            "${getString(R.string.Odd)} ${getString(R.string.Tuesday)}"
                    }
                    Calendar.WEDNESDAY -> {
                        if (date.even)
                            "${getString(R.string.Even_f)} ${getString(R.string.Wednesday)}"
                        else
                            "${getString(R.string.Odd_f)} ${getString(R.string.Wednesday)}"
                    }
                    Calendar.THURSDAY -> {
                        if (date.even)
                            "${getString(R.string.Even)} ${getString(R.string.Thursday)}"
                        else
                            "${getString(R.string.Odd)} ${getString(R.string.Thursday)}"
                    }
                    Calendar.FRIDAY -> {
                        if (date.even)
                            "${getString(R.string.Even)} ${getString(R.string.Friday)}"
                        else
                            "${getString(R.string.Odd)} ${getString(R.string.Friday)}"
                    }
                    Calendar.SATURDAY -> {
                        if (date.even)
                            "${getString(R.string.Even_f)} ${getString(R.string.Saturday)}"
                        else
                            "${getString(R.string.Odd_f)} ${getString(R.string.Saturday)}"
                    }
                    else -> {
                        getString(R.string.unknown_day)
                    }
                }
            }
        }

        fun mapWeekDayStringToCalendarWeekDay(weekday: String): Int {
            return when (weekday) {
                "Mon" -> Calendar.MONDAY
                "Tue" -> Calendar.TUESDAY
                "Wed" -> Calendar.WEDNESDAY
                "Thu" -> Calendar.THURSDAY
                "Fri" -> Calendar.FRIDAY
                "Sat" -> Calendar.SATURDAY
                "Sun" -> Calendar.SUNDAY
                else -> Calendar.SUNDAY
            }
        }

        fun mapParityToBoolean(parity: String): Boolean {
            return when (parity) {
                "Odd" -> false
                "Even" -> true
                else -> true
            }
        }
    }
}