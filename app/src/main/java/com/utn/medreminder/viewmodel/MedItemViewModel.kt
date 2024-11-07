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
                medItems.clear()
                medItems.addAll(items)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addMedItem(medItem: MedItem) {
        viewModelScope.launch {
            try {
                val newItem = RetrofitInstance.api.createMedItem(medItem)
                medItems.add(newItem)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteMedItem(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteMedItem(id)
                medItems.removeAll { it.id == id }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}