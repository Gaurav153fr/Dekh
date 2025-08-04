package com.pure.dekh

import java.text.DateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale


fun getDateStringFromMillis(millis: Long): RemaingTime {
    if (millis <= 0) return RemaingTime(0,0,0,0)

    val seconds = millis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return RemaingTime(days.toInt(), (hours % 24).toInt(), (minutes % 60).toInt(), (seconds % 60).toInt())

}

data class RemaingTime(val days: Int, val hours: Int, val minutes: Int, val seconds: Int)