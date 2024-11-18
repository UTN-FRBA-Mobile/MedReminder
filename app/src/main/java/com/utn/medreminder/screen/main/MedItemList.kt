package com.utn.medreminder.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

import com.utn.medreminder.R
import com.utn.medreminder.model.MedItem

@Composable

fun MedItemList(items: List<MedItem>, onDelete: (Long) -> Unit, onEdit: (Long) -> Unit) {

    val openAlertDeleteDialog = remember { mutableStateOf(false) }
    val id = remember { mutableStateOf<Long>(0) }
    val name = remember { mutableStateOf<String>("") }

    when {
        openAlertDeleteDialog.value->{
                AlertDeleteDialog(
                    onDismissRequest = {
                        openAlertDeleteDialog.value = false
                    },
                    onConfirmation = {
                        openAlertDeleteDialog.value=false
                        onDelete(id.value)
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        items(items) { item ->
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
                    Text(
                        text = "Frecuencia: ${item.frecuencia}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
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

                        Button(
                            onClick = { item.id?.let { onEdit(it) } },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = "Editar",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}