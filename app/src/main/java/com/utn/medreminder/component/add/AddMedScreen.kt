package com.utn.medreminder.component.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.utils.PreferencesManager
import com.utn.medreminder.utils.ScreenConst
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedScreen (navController: NavController,onSave: (MedItem) -> Unit) {
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(LocalTime.now().toString()) }
    val preferencesManager = PreferencesManager(LocalContext.current)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Agregar Medicamento") })
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ){
            TextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Nombre del Medicamento") }
            )
            TextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosis") }
            )
            TextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frecuencia") }
            )
            TextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Hora de Inicio") },
                placeholder = { Text("HH:MM") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val medication = MedItem(medicamento = medicationName,
                        dosis = dosage,
                        frecuencia=frequency)
                    //onSave(medication) // Manejar el guardado
                    preferencesManager.addMedication(medication);
                    navController.navigate(ScreenConst.ListScreenName)
                }
            ) {
                Text("Guardar Medicamento")
            }
        }
    }

}