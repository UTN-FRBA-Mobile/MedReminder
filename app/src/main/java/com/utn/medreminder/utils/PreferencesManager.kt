package com.utn.medreminder.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utn.medreminder.model.MedItem

class PreferencesManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("medication", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveMedications(medications: List<MedItem>) {
        val medicationsJson = gson.toJson(medications)
        sharedPreferences.edit().putString("medications_list", medicationsJson).apply()
    }

    fun getMedications(): List<MedItem> {
        val json = sharedPreferences.getString("medications_list", null)
        val type = object : TypeToken<List<MedItem>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun updateMedication(updatedMedication: MedItem) {
        val medications = getMedications().toMutableList()
        val index = medications.indexOfFirst { it.medicamento == updatedMedication.medicamento }
        if (index != -1) {
            medications[index] = updatedMedication
            saveMedications(medications)
        }
    }

    fun deleteMedication(medicamento: String) {
        val medications = getMedications().toMutableList()
        val updatedMedications = medications.filterNot { it.medicamento == medicamento }
        saveMedications(updatedMedications)
    }

    fun addMedication(newMedication: MedItem) {
        val medications = getMedications().toMutableList()
        medications.add(newMedication)
        saveMedications(medications)
    }

}