package com.utn.medreminder.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.utn.medreminder.model.MedAlarm
import com.utn.medreminder.utils.MedAlarmStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AlarmInfoDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    alarms: () -> List<MedAlarm>?,
    medicamento:  String
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text("Cerrar")
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icono al lado del título
                    Icon(
                        painter = painterResource(id = com.utn.medreminder.R.drawable.alarm_icon), // Reemplazar con tu icono
                        contentDescription = "Icono de alarma",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Alarmas para ${medicamento}", style = MaterialTheme.typography.titleMedium)
                }
            },
            text = {
                val alarmList = alarms() ?: emptyList()
                if (alarmList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(alarmList) { alarm ->
                            AlarmCard(alarm = alarm)
                        }
                    }
                } else {
                    Text("No hay alarmas configuradas.")
                }
            }
        )
    }
}

@Composable
fun AlarmCard(alarm: MedAlarm) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dateTime = alarm.alarmDateTime?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
    }

    val formattedDate = dateTime?.format(formatter) ?: "Fecha desconocida"
    val dayOfMonth = dateTime?.dayOfMonth ?: "??"
    val month = dateTime?.month?.getDisplayName(java.time.format.TextStyle.SHORT, Locale("es")) ?: "Mes"
    val dayOfWeek = dateTime?.dayOfWeek?.getDisplayName(java.time.format.TextStyle.FULL, Locale("es")) ?: "Día"
    val hourAndMinute = "%02d:%02d".format(alarm.alarmHour ?: 0, alarm.alarmMinute ?: 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Línea para ID y Fecha completa
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Text(
//                    text = "ID: ${alarm.id ?: "Desconocido"}",
//                    style = MaterialTheme.typography.bodyMedium
//                )

                Icon(
                    painter = if (MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.FINISHED) {
                        painterResource(id = com.utn.medreminder.R.drawable.finish_icon)
                    } else if (MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.WAITING) {
                        painterResource(id = com.utn.medreminder.R.drawable.waiting_icon) // Replace with a different icon if needed
                    }else{
                        painterResource(id = com.utn.medreminder.R.drawable.ready_icon) // Replace with a different icon if needed
                    },
                    contentDescription = "Icono de alarma",
                    modifier = Modifier.size(24.dp),
                    tint = if (MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.FINISHED){
                        MaterialTheme.colorScheme.primary
                    }else if (MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.WAITING) {
                        MaterialTheme.colorScheme.secondary
                    }
                    else{
                        MaterialTheme.colorScheme.secondary
                    }
                )

                Text(
                    text = "${if (MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.FINISHED) "Finalizado" 
                    else if(MedAlarmStatus.fromChar(alarm.status) == MedAlarmStatus.WAITING) "En Espera" else "Próximo" }",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Fecha: $formattedDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Día, día del mes y mes ofuscado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Día: $dayOfWeek $dayOfMonth, $month",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Hora: $hourAndMinute",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
