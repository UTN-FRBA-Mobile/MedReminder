package com.utn.medreminder.scheduler

import android.content.Context
import java.util.Calendar

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

    fun setAlarmAfterDelay(context: Context, alarmId: Int, delayInMinutes: Int) {
        val delayInMillis = delayInMinutes * 60 * 1000L // Convertir minutos a milisegundos
        val triggerAtMillis = System.currentTimeMillis() + delayInMillis // Tiempo actual + demora

        val alarmScheduler = AlarmScheduler(context)
        alarmScheduler.scheduleAlarm(alarmId, triggerAtMillis)

        println("Alarma programada para dentro de $delayInMinutes minutos.")
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
            e.printStackTrace() // Esto puede ayudarte a ver el error en Logcat
        }
    }

}
