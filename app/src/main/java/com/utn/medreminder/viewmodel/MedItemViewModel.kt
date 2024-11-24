package com.utn.medreminder.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utn.medreminder.api.RetrofitInstance
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.scheduler.AlarmUtils
import com.utn.medreminder.utils.MedAlarmWithItem
import com.utn.medreminder.utils.PreferencesManager
import kotlinx.coroutines.launch

class MedItemViewModel:ViewModel() {

    var medItems = mutableStateListOf<MedItem>()
        private set

    fun fetchMedItems() {
        viewModelScope.launch {
            try {
                val items = RetrofitInstance.api.getMedItems()
                medItems.clear() // Limpiar la lista antes de agregar los nuevos elementos
                medItems.addAll(items) // Añadir los items nuevos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun addMedItem(medItem: MedItem, context: Context, preferencesManager:PreferencesManager) {
        //viewModelScope.launch {
            try {
                val newItem = RetrofitInstance.api.createMedItem(medItem)
                medItems.add(newItem) // Agregar el nuevo item a la lista
                  // Guarda el MedItem individualmente
                val medId = newItem.id!!.toInt()  // Convierte id a Int con aserción de no-null
                AlarmUtils.setAlarmAfterDelayInSeconds(context,medId, 5)
                preferencesManager.addMedicationWithItem( MedAlarmWithItem(
                    idReqCodeAlarm = medId.toLong(),  // Ejemplo de identificador para el código de la alarma
                    message = "Es hora de tomar --> ${newItem.medicamento}",  // Mensaje de la alarma
                    idAlarmMed = newItem.alarms!!.sortedBy { it.id }.first().id, // Ordena por ID y toma el primero
                    idMed = medId.toLong()  // Ejemplo de ID del medicamento
                ))

                fetchMedItems()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        //}
    }

    fun deleteMedItem(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteMedItem(id)
                if(response.isSuccessful){
                    medItems.removeAll { it.id == id } // Eliminar el item de la lista
                    fetchMedItems()
                }else{
                    println("Error al eliminar el item: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMedItem(medItem: MedItem) {
        viewModelScope.launch {
            try {
                val response = medItem.id?.let { RetrofitInstance.api.updateMedItem(it, medItem) }
                if (response != null) {
                    if (response.isSuccessful) {
                        fetchMedItems()
                    } else {
                        println("Error al actualizar el medicamento: ${response.code()}")
                    }
                } else {
                    println("Error al actualizar el medicamento")
                }
            } catch (e: Exception) {
                println("Error de red: ${e.message}")
            }
        }
    }

    suspend fun getMedItem(id: Long): MedItem {
        var medItem: MedItem = MedItem()
        try {
            medItem = RetrofitInstance.api.getMedItem(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return medItem
    }
}