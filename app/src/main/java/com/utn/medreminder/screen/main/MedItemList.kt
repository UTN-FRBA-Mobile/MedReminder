package com.utn.medreminder.screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.utn.medreminder.R
import com.utn.medreminder.api.RetrofitInstance
import com.utn.medreminder.model.MedItem

@Composable

fun MedItemList(items:List<MedItem>,){

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
}