package com.utn.medreminder.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PreferencesManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("alarms", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Función para guardar la lista de MedAlarmWithItem
    fun saveAlarmsWithItems(alarmsWithItems: List<MedAlarmWithItem>) {
        val alarmsWithItemsJson = gson.toJson(alarmsWithItems)
        sharedPreferences.edit().putString("alarm_item_list", alarmsWithItemsJson).apply()
    }

    // Función para obtener la lista de MedAlarmWithItem
    fun getAlarmsWithItems(): List<MedAlarmWithItem> {
        val json = sharedPreferences.getString("alarm_item_list", null)
        val type = object : TypeToken<List<MedAlarmWithItem>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    // Función para obtener un MedAlarmWithItem por id
    fun getMedAlarmWithItemById(idReqCodeAlarm: Long): MedAlarmWithItem? {
        val alarmsWithItems = getAlarmsWithItems()
        return alarmsWithItems.find { it.idReqCodeAlarm == idReqCodeAlarm }
    }

    // Función para actualizar un MedAlarmWithItem
    fun updateMedicationWithItem(updatedItem: MedAlarmWithItem) {
        val alarmsWithItems = getAlarmsWithItems().toMutableList()
        val index = alarmsWithItems.indexOfFirst { it.idReqCodeAlarm == updatedItem.idReqCodeAlarm }
        if (index != -1) {
            alarmsWithItems[index] = updatedItem
            saveAlarmsWithItems(alarmsWithItems)
        }
    }

    // Función para eliminar un MedAlarmWithItem por ID
    fun deleteMedicationWithItem(idReqCodeAlarm: Long) {
        val alarmsWithItems = getAlarmsWithItems().toMutableList()
        val updatedAlarmsWithItems = alarmsWithItems.filterNot { it.idReqCodeAlarm == idReqCodeAlarm }
        saveAlarmsWithItems(updatedAlarmsWithItems)
    }

    // Función para agregar un MedAlarmWithItem
    fun addMedicationWithItem(newItem: MedAlarmWithItem) {
        val alarmsWithItems = getAlarmsWithItems().toMutableList()
        alarmsWithItems.add(newItem)
        saveAlarmsWithItems(alarmsWithItems)
    }
}
