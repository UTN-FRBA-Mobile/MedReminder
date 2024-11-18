package com.utn.medreminder

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utn.medreminder.screen.add.AddMedScreen
import com.utn.medreminder.screen.edit.EditMedScreen
import com.utn.medreminder.screen.main.MainScreen
import com.utn.medreminder.ui.theme.MyApplicationTheme
import com.utn.medreminder.utils.ScreenConst
import android.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MedRemindApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // Verifica si es Android 13 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Verifica si ya se tiene el permiso para mostrar notificaciones
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Si no se tiene el permiso, solicita el permiso
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    // Manejo de resultados de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El permiso fue concedido, puedes mostrar notificaciones
            } else {
                // El permiso fue denegado, maneja esta situación si es necesario
            }
        }
    }

}


@Composable
fun MedRemindApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController();
//    MedItemList(list)
    NavHost(navController = navController, startDestination = ScreenConst.ListScreenName ){
        composable(route = ScreenConst.ListScreenName) {
            MainScreen(navController = navController);
        }
        composable(route =ScreenConst.AddItemScreenName){
            AddMedScreen(navController = navController)
        }
        composable(route =ScreenConst.EditItemScreenName) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0
            EditMedScreen(navController=navController, medItemId=id)
        }
    }

}