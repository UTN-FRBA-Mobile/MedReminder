package com.utn.medreminder.screen.edit

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.utils.ScreenConst
import com.utn.medreminder.viewmodel.MedItemViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedScreen(navController: NavController, viewModel: MedItemViewModel = viewModel(), medItemId: Long) {
    // Estado de los campos
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }

    // Contexto y scope para coroutine
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    println("llamada")

    // Cargar el medicamento desde la API
    LaunchedEffect(medItemId) {
        try {
            // Recupera el medicamento para la edición
            val medItem = viewModel.getMedItem(medItemId)
            medicationName = medItem.medicamento
            dosage = medItem.dosis
            frequency = medItem.frecuencia
            startTime = medItem.horaInicio
            println("medicationName")
            println(medicationName)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al cargar el medicamento", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Medicamento") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Nombre del Medicamento") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosis") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frecuencia") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Hora de Inicio") },
                placeholder = { Text("HH:MM") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (medicationName.isBlank() || dosage.isBlank() || frequency.isBlank() || startTime.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor, complete todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        scope.launch {
                            try {
                                // Crea el objeto MedItem con los nuevos valores
                                val updatedMedItem = MedItem(
                                    id = medItemId, // Usar el mismo ID para la edición
                                    medicamento = medicationName,
                                    dosis = dosage,
                                    frecuencia = frequency,
                                    horaInicio = startTime
                                )

                                // Llama a la API para actualizar el medicamento
                                viewModel.updateMedItem(updatedMedItem)

                                // Navega de regreso a la lista
                                navController.navigate(ScreenConst.ListScreenName)

                                // Muestra un mensaje de éxito
                                Toast.makeText(
                                    context,
                                    "Medicamento actualizado exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                // Maneja el error y muestra un mensaje
                                Toast.makeText(
                                    context,
                                    "Error: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}
