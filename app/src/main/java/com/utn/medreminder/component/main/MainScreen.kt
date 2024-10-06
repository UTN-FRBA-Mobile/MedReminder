package com.utn.medreminder.component.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier

@Composable
fun MedItemList(items: List<MedItem>, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        MedItemList(items)
        AddItemButton(navController = navController)
    }
}