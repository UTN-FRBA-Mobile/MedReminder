package com.utn.medreminder.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utn.medreminder.api.RetrofitInstance
import com.utn.medreminder.model.MedItem
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

    suspend fun addMedItem(medItem: MedItem) {
        //viewModelScope.launch {
            try {
                val newItem = RetrofitInstance.api.createMedItem(medItem)
                medItems.add(newItem) // Agregar el nuevo item a la lista
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