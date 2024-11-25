package com.utn.medreminder.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

import com.utn.medreminder.R
import com.utn.medreminder.model.MedItem
import com.utn.medreminder.scheduler.AlarmScheduler
import com.utn.medreminder.scheduler.AlarmUtils
import com.utn.medreminder.utils.MedAlarmStatus
import com.utn.medreminder.utils.PreferencesManager

@Composable

fun MedItemList(items: List<MedItem>, onDelete: (Long) -> Unit, onEdit: (Long) -> Unit) {

    val openAlertDeleteDialog = remember { mutableStateOf(false) }
    val id = remember { mutableStateOf<Long>(0) }
    val name = remember { mutableStateOf<String>("") }
    val context = LocalContext.current // Si estás en una Activity o usa requireContext() en un Fragment
    val preferencesManager = PreferencesManager(context)

    when {
        openAlertDeleteDialog.value->{
                AlertDeleteDialog(
                    onDismissRequest = {
                        openAlertDeleteDialog.value = false
                    },
                    onConfirmation = {
                        openAlertDeleteDialog.value=false
                        onDelete(id.value)
                        AlarmScheduler(context).cancelAlarm(id.value.toInt())
                        preferencesManager.deleteMedicationWithItem(id.value);
                        println("Here--> Se confirmo, implementar lo que hay q hacer")
                    },
                    dialogTitle = "Eliminar Medicamento",

                    //dialogText = "¿Esta seguro/a que desea eliminar el medicamento ${name.value}?",

                    dialogText = buildAnnotatedString {
                        append("¿Está seguro/a que desea eliminar el medicamento ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(name.value)
                        }
                        append("?")
                    },
                    icon = Icons.Default.Info
                )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Medicamentos Programados",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold, // Hacemos el texto un poco más pesado
                color = MaterialTheme.colorScheme.primary, // Color que se ajusta al tema
            ),
            modifier = Modifier
                .padding(top = 32.dp, bottom = 8.dp, start = 16.dp, end = 16.dp), // Espaciado alrededor
        )


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(items) { item ->
            val openInfoDialog = remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.medicine_icon),
                                contentDescription = "Ícono de medicamento",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))


                            Text(
                                text = item.medicamento,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )


                        }
                        Text(
                            text = "Dosis: ${item.dosis}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Columna para la información de "Frecuencia", "Dosis Tomadas", y "Dosis Faltantes"
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Frecuencia: ${item.frecuencia}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Dosis Tomadas: ${item.statusCount!!.finishedCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Dosis Faltantes: ${item.statusCount!!.readyCount + item.statusCount!!.waitingCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            )
                            {
                            Icon(
                                painter = if (item.statusCount!!.readyCount == 0 && item.statusCount!!.waitingCount == 0)
                                    painterResource(id = com.utn.medreminder.R.drawable.finish_icon)
                                    else painterResource(id = com.utn.medreminder.R.drawable.time_left),
                                contentDescription = "Icono de alarma",
                                modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                                tint = if (item.statusCount!!.readyCount == 0 && item.statusCount!!.waitingCount == 0)
                                    Color(0xFF4CAF50) else Color(0xFFFF9800)
                            )
                            Text(
                            text = if (item.statusCount!!.readyCount == 0 && item.statusCount!!.waitingCount == 0) "Finalizado" else "Pendiente",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (item.statusCount!!.readyCount == 0 && item.statusCount!!.waitingCount == 0)
                                Color(0xFF4CAF50) else Color(0xFFFF9800),
                            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp)
                        )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {



                        Button(onClick = { openInfoDialog.value = true }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Icon(
                                //  painter = painterResource(id = R.drawable.alarm_icon), // Reemplaza con el recurso de tu ícono de alarma
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Ver alarmas",
                                tint = Color(0xFFFFFFFF)
                            )

                            Text(
                                text = "Ver Alarmas",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Button(onClick = {
                                item?.let {
                                    openAlertDeleteDialog.value = true;
                                    id.value = it.id ?: 0
                                    name.value = it.medicamento
                                }
                            }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                            Text(
                                text = "Eliminar",
                                color = MaterialTheme.colorScheme.onError,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        IconButton(onClick = { openInfoDialog.value = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Ver alarma",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(48.dp)  // Ajusta el tamaño según lo necesites
                            )
                        }

                    }
                }
            }

            AlarmInfoDialog(
                openDialog = openInfoDialog.value,
                onDismissRequest = { openInfoDialog.value = false },
                alarms = {item.alarms},
                medicamento = item.medicamento
            )
        }
    }
}


}