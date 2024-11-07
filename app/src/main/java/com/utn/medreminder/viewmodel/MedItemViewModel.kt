package com.utn.medreminder.viewmodel

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

    fun addMedItem(medItem: MedItem) {
        viewModelScope.launch {
            try {
                val newItem = RetrofitInstance.api.createMedItem(medItem)
                medItems.add(newItem) // Agregar el nuevo item a la lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteMedItem(id: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteMedItem(id)
                if(response.isSuccessful){
                    medItems.removeAll { it.id == id } // Eliminar el item de la lista
                }else{
                    println("Error al eliminar el item: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}