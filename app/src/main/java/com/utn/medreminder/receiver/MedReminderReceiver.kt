package com.utn.medreminder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.utn.medreminder.MainActivity
import com.utn.medreminder.api.RetrofitInstanceAlarmMed
import com.utn.medreminder.helper.NotificationHelper
import com.utn.medreminder.scheduler.AlarmScheduler
import com.utn.medreminder.scheduler.AlarmUtils
import com.utn.medreminder.utils.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedReminderReceiver : BroadcastReceiver() {

    private lateinit var preferencesManager: PreferencesManager

    override fun onReceive(context: Context, intent: Intent) {
        preferencesManager = PreferencesManager(context)
        val reminderId = intent.getIntExtra("reminder_id", -1)
        val medAlarmWithItem = preferencesManager.getMedAlarmWithItemById(reminderId.toLong());
        if (reminderId != -1) {
            NotificationHelper.showNotification(
                context,
                medAlarmWithItem!!.message,
                "Reminder ID: $reminderId"
            )
            //Actualizar a Terminado
            //Consultar la lista de alarmas
            //De la lista de alarmas agarrar la alarma que tiene fecha mas proxima y este en estado de waiting
            //-->http://localhost:8085/api/alarm-med-items/next-alarm-by-medId/${medAlarmWithItem.idMed}
            //Con la alarma se debera llamar al alarm Scheduler y activar la alarma
            //--->val alarmScheduler = AlarmScheduler(context);
            //--->alarmScheduler.scheduleAlarm(medAlarmWithItem.idMed, triggerAtMillis);

            //Si la lista esta vacia, es decir que no hay alarma con estado waiting o pendiente para correr se debe cancelar la alrma
            //alarmScheduler.cancelAlarm(alarmId);


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response =
                        RetrofitInstanceAlarmMed.api.finishAlarmStatus(medAlarmWithItem.idAlarmMed!!)
                    if (response.isSuccessful) { // SI la alarma se actualizó correctamente , buscar la siguiente alarma
                        val nextAlarm =
                            RetrofitInstanceAlarmMed.api.getNextAlarmByMedId(medAlarmWithItem.idMed)
                        if (nextAlarm != null) {  // SI hay siguiente alarma, actualizo el sharedPref y mando a correr con el mismo id
                            medAlarmWithItem.idAlarmMed = nextAlarm.id!!
                            preferencesManager.updateMedicationWithItem(medAlarmWithItem);
                            AlarmUtils.setAlarmAfterDelayInSeconds(context,reminderId, 5)
                            //(context as MainActivity).medItemViewModel.updateMedItems(updatedMedItems)

                        } else {  // Si no hay siguiente alarma se cancela y se elimina del sharedPref
                            val alarmScheduler = AlarmScheduler(context)
                            alarmScheduler.cancelAlarm(reminderId)
                            preferencesManager.deleteMedicationWithItem(reminderId.toLong())
                        }
                    } else {
                        println("Error al actualizar el estado: ${response.code()}")
                    }
                } catch (e: Exception) {
                    val alarmScheduler = AlarmScheduler(context)
                    alarmScheduler.cancelAlarm(reminderId)
                    preferencesManager.deleteMedicationWithItem(reminderId.toLong())
                    println("Error en la API: ${e.message}")
                }
            }
        }
    }
}