package com.utn.medreminder.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utn.medreminder.api.RetrofitInstance
import com.utn.medreminder.api.RetrofitInstanceAlarmMed
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.scheduler.AlarmUtils
import com.utn.medreminder.utils.ConvDateUtils
import com.utn.medreminder.utils.MedAlarmWithItem
import com.utn.medreminder.utils.PreferencesManager
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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
                val  nextAlarm= newItem.alarms!!.sortedBy { it.id }.first();

                val medAlarm=MedAlarmWithItem(
                    idReqCodeAlarm = medId.toLong(),  // Ejemplo de identificador para el código de la alarma
                    message = "Es hora de tomar --> ${newItem.medicamento}",  // Mensaje de la alarma
                    idAlarmMed = newItem.alarms!!.sortedBy { it.id }.first().id, // Ordena por ID y toma el primero
                    idMed = medId.toLong(),  // Ejemplo de ID del medicamento
                    alarmDateTime = nextAlarm.alarmDateTime,
                )
                preferencesManager.addMedicationWithItem( medAlarm)
                val secondsDelay = ConvDateUtils.calculateSecondsUntil(medAlarm.alarmDateTime!!)
                AlarmUtils.setAlarmAfterDelayInSeconds(context,medId, secondsDelay.toInt())
                RetrofitInstanceAlarmMed.api.readyAlarmStatus(nextAlarm.id!!.toLong())
                //AlarmUtils.setAlarmAfterDelayByDateTimeAlarm(context,medId, medAlarm.alarmDateTime!!)
                fetchMedItems()
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = errorBody ?: "Error desconocido del servidor"
                e.printStackTrace()
                throw Exception("$errorMessage")
            }catch (e: IOException) {
                e.printStackTrace()
                throw Exception("Error de red o de conexión: ${e.message}")
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
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