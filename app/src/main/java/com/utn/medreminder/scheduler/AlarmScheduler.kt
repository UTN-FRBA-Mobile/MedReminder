package com.utn.medreminder.scheduler


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.utn.medreminder.receiver.MedReminderReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AlarmScheduler(private val context: Context) {


    fun scheduleAlarm(id: Int, timeInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MedReminderReceiver::class.java).apply {
            putExtra("reminder_id", id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    fun scheduleAlarmByDateTimeStr(id: Int, alarmDateTime: String) {
        try {
            // Parsear la fecha y hora de la alarma (formato "yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val alarmTime = formatter.parse(alarmDateTime) ?: throw Exception("Invalid date format")

            // Calcular el tiempo hasta la alarma en milisegundos
            val currentTimeMillis = System.currentTimeMillis() // Hora actual en milisegundos
            val alarmTimeMillis = alarmTime.time // Hora de la alarma en milisegundos
            val delayInMillis =
                alarmTimeMillis - currentTimeMillis // Diferencia entre hora actual y hora de la alarma

            // Verificar que el delay no sea negativo
            if (delayInMillis <= 0) {
                println("La hora de la alarma ya pasó.")
                return
            }

            // Mostrar log con el tiempo
            println("Alarma programada para $delayInMillis milisegundos.")

            // Programar la alarma usando AlarmManager
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MedReminderReceiver::class.java).apply {
                putExtra("reminder_id", id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTimeMillis,
                pendingIntent
            )

        } catch (e: Exception) {
            e.printStackTrace() // Esto puede ayudarte a ver el error en Logcat
        }
    }

    fun cancelAlarm(id: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MedReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

}
