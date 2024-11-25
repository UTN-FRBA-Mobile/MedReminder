package com.utn.medreminder.screen.add

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun AlertErrorDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: AnnotatedString,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon,                modifier = Modifier.size(32.dp) // Aplica el tamaño
                , contentDescription = "Error Icon", tint = MaterialTheme.colorScheme.error)
        },
        title = {
            Text(text = dialogTitle, color = MaterialTheme.colorScheme.error)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { onDismissRequest() },
            ) {
                Text("Aceptar", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}