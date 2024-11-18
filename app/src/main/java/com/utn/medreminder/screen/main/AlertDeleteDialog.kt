package com.utn.medreminder.screen.main

import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString

@Composable
fun AlertDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: AnnotatedString,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon",    tint = MaterialTheme.colorScheme.primary )
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() },
               // colors = ButtonDefaults.textButtonColors(
               //     containerColor = MaterialTheme.colorScheme.primary, // Color de fondo del botón
               //     contentColor = MaterialTheme.colorScheme.onPrimary // Color del texto del botón
               // )
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
//                colors = ButtonDefaults.textButtonColors(
//                    containerColor = MaterialTheme.colorScheme.error, // Color de fondo del botón
//                    contentColor = MaterialTheme.colorScheme.onError // Color del texto del botón
//                )
            ) {
                Text("Cancelar")
            }
        }
    )
}