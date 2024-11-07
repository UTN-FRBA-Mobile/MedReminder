package com.utn.medreminder.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utn.medreminder.viewmodel.MedItemViewModel

@Composable
fun MainScreen(viewModel: MedItemViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(Unit) {
        viewModel.fetchMedItems()
    }
    val medItems = viewModel.medItems
    Box(modifier = Modifier.fillMaxSize()) {
        MedItemList(items = medItems)
        AddItemButton(navController = navController)
    }
}