package com.example.laboratory1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratory1.ui.theme.Laboratory1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratory1Theme{
                // A surface container using the 'background' color from the theme
                //list of modifiers: https://developer.android.com/jetpack/compose/modifiers-list
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    MyScreen()
                }
            }
        }
    }
}



@Composable
fun MyScreen()
{
    val n = 30
    val order : MutableList<Int> = List(n){0}.toMutableStateList()

    val sub : (Int) -> Unit = {i:Int -> if (order[i]>0) order[i]--}
    val add : (Int) -> Unit = { i:Int -> order[i]++}

    var msg by rememberSaveable { mutableStateOf("Submit Your order!!")
    }

    LazyColumn(content = {
        item  {
            Text(modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(),
                text="Make your choice!", fontSize = 30.sp)
        }

        items(n) {
            MyStatelessItem(it,"Item number $it",order[it].toString(),add,sub)
        }
        item {
            Button(onClick = {
                msg="Done! You order "+order.sum()+" items"
            },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = msg)
            }
        }
    }
    )
}

@Composable
fun MyStatelessItem(id: Int, s: String, count:String, add: (Int) -> Unit, sub: (Int) -> Unit)
{
    Surface (
        color= MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp))
    {
        Row (modifier = Modifier
            .fillMaxWidth()){
            Column (
                modifier = Modifier.weight(1f))

            {
                Text(s)
                Text("$count")
            }

            ElevatedButton(onClick = {
                add(id)
            }
            )
            {
                Text("Add one")
            }
            ElevatedButton(onClick = {
                sub(id)
            })
            {
                Text("Sub one")
            }
        }
    }
}

@Composable
fun MyItem(name: String =""){
    //by allow to modify the mutableState as a normal variable
    //setter/getter method not required
    var count by rememberSaveable { mutableStateOf(0)
    }

    Surface (
        color= MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp))
    {
        Row (modifier = Modifier
            .fillMaxWidth()){
            Column (
                modifier = Modifier.weight(1f))

            {
                Text(name)
                Text("$count")
            }
            ElevatedButton(onClick = { count++ })
            {
                Text("Add one")
            }
            ElevatedButton(onClick = { if (count>0) count-- })
            {
                Text("Sub one")
            }
        }
    }

}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var count by remember { mutableStateOf(0) }

    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(text = "$count")

        for ( i in 1..100){
            Button(onClick = { count++ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Click me!")
            }
        }
    }

}

