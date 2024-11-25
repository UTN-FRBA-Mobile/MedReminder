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
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.utn.medreminder.receiver.MedReminderReceiver
import com.utn.medreminder.viewmodel.MedItemViewModel

class MainActivity : ComponentActivity() {

    private val medItemViewModel: MedItemViewModel by viewModels()
    private lateinit var updateReceiver: BroadcastReceiver  // Usa MedReminderReceiver en lugar de BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        Image(
                            painter = painterResource(id = R.drawable.background_gen), // Asegúrate de que el nombre del archivo sea correcto
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Esto hace que la imagen cubra toda la pantalla
                        )
                    MedRemindApp(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = medItemViewModel
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

        // Inicializa el BroadcastReceiver
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                medItemViewModel.fetchMedItems() // Actualizar los ítems cuando llega el broadcast
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("com.utn.medreminder.UPDATE_MED_ITEMS")
        registerReceiver(updateReceiver, filter,Context.RECEIVER_EXPORTED)  // Registrar el receptor
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(updateReceiver)  // Asegúrate de desregistrar el receptor
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
fun MedRemindApp(modifier: Modifier = Modifier, viewModel: MedItemViewModel) {
    val navController = rememberNavController();
//    MedItemList(list)


    NavHost(navController = navController, startDestination = ScreenConst.ListScreenName ){
        composable(route = ScreenConst.ListScreenName) {
            MainScreen(navController = navController, viewModel = viewModel);
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