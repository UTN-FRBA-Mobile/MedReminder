package com.utn.medreminder.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.utn.medreminder.R
import com.utn.medreminder.utils.ScreenConst


@Composable
fun AddItemButton(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) { // El Box asegura que el FAB se sitúe en la parte inferior derecha
        ExtendedFloatingActionButton(
            onClick = { navController.navigate(ScreenConst.AddItemScreenName) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), // Añadimos un padding más espacioso
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Agregar medicamento",
                    modifier = Modifier.size(24.dp)
                )
            },
            text = {
                Text(
                    text = "Agregar Medicamento",
                    modifier = Modifier.padding(start = 8.dp) // Un pequeño padding para separar el texto del ícono
                )
            },
            containerColor = MaterialTheme.colorScheme.primary, // Usa el color principal del tema
            contentColor = Color.White // Asegura que el ícono y texto sean blancos para buena visibilidad
        )
    }
}