package com.utn.medreminder.component.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.utils.PreferencesManager

@Composable
fun MedItemList(items: List<MedItem>, navController: NavController) {
    val preferencesManager = PreferencesManager(LocalContext.current)
    Box(modifier = Modifier.fillMaxSize()) {
        MedItemList(preferencesManager.getMedications())
        AddItemButton(navController = navController)
    }
}