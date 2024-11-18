package com.utn.medreminder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.utn.medreminder.helper.NotificationHelper

class MedReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra("reminder_id", -1)
        if (reminderId != -1) {
            NotificationHelper.showNotification(context, "Time to take your medication!", "Reminder ID: $reminderId")
        }
    }
}