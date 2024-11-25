package com.utn.medreminder.scheduler


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.utn.medreminder.receiver.MedReminderReceiver
import java.util.Calendar

class AlarmScheduler(private val context: Context) {

    fun scheduleAlarm(id: Int, timeInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MedReminderReceiver::class.java).apply {
            putExtra("reminder_id", id)
        }

//        val reminderIds = intent.getIntArrayExtra("reminder_id")
//        if (reminderIds != null) {
//            reminderIds.forEach { id ->
//                println("reminder_id --> Id Reminder $id")
//            }
//        } else {
//            // Caso: Es un único entero
//            val singleReminderId = intent.getIntExtra("reminder_id", -1)
//            if (singleReminderId != -1) {
//                println("reminder_id --> Id Reminder $singleReminderId")
//            } else {
//                println("reminder_id --> No se encontró ningún ID de recordatorio")
//            }
//        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
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
