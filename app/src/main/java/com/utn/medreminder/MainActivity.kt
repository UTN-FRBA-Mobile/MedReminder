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
import androidx.compose.ui.tooling.preview.Preview
import com.utn.medreminder.component.main.MedItem
import com.utn.medreminder.component.main.MedItemList
import com.utn.medreminder.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        list = emptyList(),
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(list: List<MedItem>,name:String ,  modifier: Modifier = Modifier) {

//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
    MedItemList(list)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {

        val items = List(100) {
            MedItem(
                medicamento = "Medicamento #$it",
                dosis = "${it + 1} mg",
                frecuencia = "${it % 3 + 1} veces al día"
            )
        }

        Greeting(items,"hello world!!!")
    }
}