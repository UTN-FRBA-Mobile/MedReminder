package com.utn.medreminder.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.Duration

object  ConvDateUtils {
    fun calculateSecondsUntil(alarmDateTime: String): Long {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val alarmTime = LocalDateTime.parse(alarmDateTime, formatter)
        val now = LocalDateTime.now(ZoneId.systemDefault())
        val duration = Duration.between(now, alarmTime)
        println("now: ${now}; alarmTime: ${alarmTime}, duration: ${duration}")
        return duration.seconds
    }
}