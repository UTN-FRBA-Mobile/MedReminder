package com.utn.medreminder.scheduler

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object AlarmUtils {

    fun getTimeInMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    fun setAlarmAfterDelayInSeconds(context: Context, alarmId: Int, delayInSeconds: Int) {
        try {
            val delayInMillis = delayInSeconds * 1000L // Convertir segundos a milisegundos
            val triggerAtMillis = System.currentTimeMillis() + delayInMillis // Tiempo actual + demora

            val alarmScheduler = AlarmScheduler(context)
            alarmScheduler.scheduleAlarm(alarmId, triggerAtMillis)

            // Mostrar log con el tiempo
            println("Alarma programada para dentro de $delayInSeconds segundos.")

            // Mostrar también en minutos y horas
            val delayInMinutes = delayInSeconds / 60
            val delayInHours = delayInMinutes / 60
            println("Alarma programada para dentro de $delayInMinutes minutos.")
            println("Alarma programada para dentro de $delayInHours horas.")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setAlarmAfterDelayByDateTimeAlarm(context: Context, alarmId: Int, dateTimeAlarm: String) {
        try {
            // Parsear la fecha y hora de la alarma (formato "yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val alarmTime = formatter.parse(dateTimeAlarm) ?: throw Exception("Invalid date format")

            // Calcular el tiempo hasta la alarma en milisegundos
            val currentTimeMillis = System.currentTimeMillis() // Hora actual en milisegundos
            val alarmTimeMillis = alarmTime.time // Hora de la alarma en milisegundos
            val delayInMillis = alarmTimeMillis - currentTimeMillis // Diferencia entre hora actual y hora de la alarma

            // Verificar que el delay no sea negativo
            if (delayInMillis <= 0) {
                println("La hora de la alarma ya pasó.")
                return
            }

            // Mostrar log con el tiempo
            println("Alarma programada para dentro de $delayInMillis milisegundos.")

            // Programar la alarma usando AlarmScheduler
            val alarmScheduler = AlarmScheduler(context)
            alarmScheduler.scheduleAlarmByDateTimeStr(alarmId, dateTimeAlarm)

            // Mostrar también en minutos y horas
            val delayInSeconds = delayInMillis / 1000
            val delayInMinutes = delayInSeconds / 60
            val delayInHours = delayInMinutes / 60
            println("Alarma programada para dentro de $delayInSeconds segundos.")
            println("Alarma programada para dentro de $delayInMinutes minutos.")
            println("Alarma programada para dentro de $delayInHours horas.")

        } catch (e: Exception) {
            e.printStackTrace() // Esto puede ayudarte a ver el error en Logcat
        }
    }
}
