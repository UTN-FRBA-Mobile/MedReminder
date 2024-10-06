package com.utn.medreminder.component.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
    Box(modifier = Modifier.fillMaxSize()) {//Porque Necesito un BOX si el MainScreen lo tiene¿?
        ExtendedFloatingActionButton(
            onClick = { navController.navigate(ScreenConst.AddItemScreenName) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 0.dp, vertical = 0.dp)
                .background(color = Color.Yellow),
            icon = {
                Icon(
                    painterResource(id = R.drawable.add_icon),
                    contentDescription = "example",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(0.dp)
                )
            },
            text = {
                Text(
                    text = "Agregar Medicamento",
                    modifier = Modifier
                        .padding(0.dp)
                        .background(color = Color.Blue)
                )
            },
        )
    }
}