package com.utn.medreminder.component.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.utn.medreminder.R

import androidx.compose.ui.Alignment

@Composable
fun MedItemList(items:List<MedItem>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(items) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .border(BorderStroke(1.dp, Color.Gray))
                        .padding(8.dp)
                ) {
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.medicine_icon), // Reemplaza con el nombre de tu archivo PNG sin la extensión
                            contentDescription = "Ícono de medicamento", // Descripción para accesibilidad
                            modifier = Modifier.size(24.dp) // Ajusta el tamaño según necesites
                        )
                        BasicText(
                            text = "Medicamento: ${item.medicamento}",
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                    // Nueva fila para dosis y frecuencia
                    Row(
                        modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween // Espaciado entre textos
                    ) {
                        BasicText(
                            text = "Dosis: ${item.dosis}",
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        VerticalDivider()
                        BasicText(
                            text = "Frecuencia: ${item.frecuencia}",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                }
            }
        }
            ExtendedFloatingActionButton(
                onClick = { },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                icon = { Icon(painterResource(id = R.drawable.medicine_icon), "example") },
                text = { Text(text = "Agregar Medicamento") }
            )
    }
}