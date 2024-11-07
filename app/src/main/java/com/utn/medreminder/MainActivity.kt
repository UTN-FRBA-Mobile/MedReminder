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
import com.utn.medreminder.screen.edit.EditMedScreen
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
            AddMedScreen(navController = navController)
        }
        composable(route =ScreenConst.EditItemScreenName) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0
            EditMedScreen(navController=navController, medItemId=id)
        }
    }

}