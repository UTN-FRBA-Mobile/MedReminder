package com.utn.medreminder.screen.add

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.utils.PreferencesManager
import com.utn.medreminder.utils.ScreenConst
import com.utn.medreminder.viewmodel.MedItemViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedScreen(navController: NavController, viewModel: MedItemViewModel = viewModel()) {
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("") } // Estado para la opción seleccionada
    var frequencyInHours by remember { mutableStateOf(0) } // Estado para la frecuencia en horas
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val preferencesManager = PreferencesManager(context)  // Inicializa PreferencesManager con el contexto
    val frequencyOptions = listOf("Cada 4 horas", "Cada 6 horas", "Cada 8 horas","Cada 12 horas","Cada 24 horas")
    val frequencyMap = mapOf(
        "Cada 4 horas" to 4,
        "Cada 6 horas" to 6,
        "Cada 8 horas" to 8,
        "Cada 12 horas" to 12,
        "Cada 24 horas" to 24
    )

    var expandedMedication by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) } // Estado para el menú desplegable
    var horaYFechaDeInicio by remember { mutableStateOf("") } // Variable para almacenar la fecha y hora en formato deseado

    // Función para abrir el selector de fecha

    // Fecha y hora actuales para los pickers
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val medicationList = listOf("Omeprazol 500mg", "Paracetamol 500mg", "Ibuprofeno 200mg", "Amoxicilina 500mg", "Aspirina 100mg")


    fun updateFechaYHora() {
        if (selectedDate.isNotBlank() && startTime.isNotBlank()) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val parsedDate = dateFormat.parse(selectedDate)

            // Si la fecha y la hora se han seleccionado correctamente, combinar en el formato deseado
            if (parsedDate != null) {
                val calendar = Calendar.getInstance()
                calendar.time = parsedDate
                val timeParts = startTime.split(":")
                calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                calendar.set(Calendar.MINUTE, timeParts[1].toInt())

                // Formatear la fecha y hora en el formato deseado
                val formattedDateTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                horaYFechaDeInicio = formattedDateTime.format(calendar.time)
            }
        }
    }


    val showDatePicker = {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/${month + 1}/$year"
                updateFechaYHora() // Actualiza la fecha y hora cuando se selecciona una fecha
            },
            currentYear, currentMonth, currentDay
        )
        datePickerDialog.show()
    }

    // Función para abrir el selector de hora
    val showTimePicker = {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                startTime = String.format("%02d:%02d", hourOfDay, minute)
                updateFechaYHora() // Actualiza la fecha y hora cuando se selecciona una hora
            },
            currentHour, currentMinute, true
        )
        timePickerDialog.show()
    }




    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Agregar Medicamento") }
            ,            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

        }
    ) { innerPadding ->

        Image(
            painter = painterResource(id = com.utn.medreminder.R.drawable.background_gen), // Asegúrate de que el nombre del archivo sea correcto
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Esto hace que la imagen cubra toda la pantalla
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expandedMedication,
                onExpandedChange = { expandedMedication = !expandedMedication }
            ) {
                OutlinedTextField(
                    value = medicationName.ifEmpty { "Seleccionar Medicamento" },
                    onValueChange = {},
                    label = { Text("Medicamento") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMedication)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true
                )

                ExposedDropdownMenu(
                    expanded = expandedMedication,
                    onDismissRequest = { expandedMedication = false }
                ) {
                    medicationList.forEach { medication ->
                        DropdownMenuItem(
                            text = { Text(medication) },
                            onClick = {
                                medicationName = medication
                                expandedMedication = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = dosage,
                onValueChange = {
                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        dosage = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Cant. Comprimidos/Dosis") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded } // Cambia el estado al hacer clic
            ) {
                OutlinedTextField(
                    value = selectedFrequency.ifEmpty { "Seleccionar Frecuencia" },
                    onValueChange = {},
                    label = { Text("Frecuencia") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true // Solo se puede seleccionar de las opciones
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    frequencyOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedFrequency = option
                                frequency = option // Actualiza la frecuencia seleccionada
                                frequencyInHours = frequencyMap[option] ?: 0 // Actualiza la frecuencia en horas
                                expanded = false // Cierra el menú al seleccionar
                            }
                        )
                    }
                }
            }

            // Input de fecha

            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                label = { Text("Fecha") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp) // Agrega padding si es necesario
                    .clickable { showDatePicker() } // La interacción está en toda el área

            )

            // Input de hora
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp) // Asegura una altura mínima adecuada
                    .clickable { showTimePicker() },
            ) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = {},
                    label = { Text("Hora de Inicio") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

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
                                // Crea el objeto MedItem
                                val medItem = MedItem(
                                    medicamento = medicationName,
                                    dosis = dosage,
                                    frecuencia = frequency,
                                    horayFechaDeInicio = horaYFechaDeInicio,
                                    frecuenciaEnHoras = frequencyInHours,
                                    cantidad = dosage.toInt()
                                )

                                // Llama a la API para guardar el medicamento
                                viewModel.addMedItem(medItem,context,preferencesManager)

                                // Navega de regreso a la lista
                                navController.navigate(ScreenConst.ListScreenName)

                                // Muestra un mensaje de éxito
                                Toast.makeText(
                                    context,
                                    "Medicamento guardado exitosamente",
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
                Text("Guardar Medicamento")
            }

            Button(
                onClick = {
                    navController.navigate(ScreenConst.ListScreenName)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("<-- Volver -->")
            }
        }
    }
}


