package com.utn.medreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utn.medreminder.screen.add.AddMedScreen
import com.utn.medreminder.screen.main.MainScreen
import com.utn.medreminder.ui.theme.MyApplicationTheme
import com.utn.medreminder.utils.ScreenConst

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
            AddMedScreen(navController = navController) {
                
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//
//        val items = List(100) {
//            MedItem(
//                medicamento = "Medicamento #$it",
//                dosis = "${it + 1} mg",
//                frecuencia = "${it % 3 + 1} veces al día"
//            )
//        }
//
//    }
//}