package com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertUtcIsoToLocalDateTimeString(isoString: String): String {
    val instant: Instant = Instant.parse(isoString)
    val systemZone: ZoneId = ZoneId.systemDefault()
    val zonedDateTime: ZonedDateTime = instant.atZone(systemZone)
    val dateTime = zonedDateTime.toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return formatter.format(dateTime)
}