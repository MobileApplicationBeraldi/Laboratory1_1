package com.example.laboratory1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratory1.ui.theme.Laboratory1Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mViewModel by viewModels<MyViewModel>()

        //Before the app starts, get the data from the remote service..
        //setContent { MyScreen(mViewModel) }

        runBlocking {
             withContext(Dispatchers.IO) {
                val j =  launch {
                     //n = mViewModel.mRemoteDataSource.get().get("n").asInt
                     mViewModel.getAll()
                 }
                j.join()
                 setContent {
                     Log.i("REPLY","n remoto:"+mViewModel.n)
                     //mViewModel.n=n
                     MyScreen(mViewModel)
                 }
             }
        }
        return
        }
}


@Composable
fun MyScreen(mViewModel : MyViewModel)
{

    val n = mViewModel.n

    LazyColumn(content = {
        item  {
            Text(modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(),
                text="Make your choice!", fontSize = 30.sp)
        }

        items(n) {

            MyStatelessItem(it,mViewModel)

        }
        item {
            Button(onClick = {
                mViewModel.submit()
                },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = mViewModel.msg)
            }
        }
    }
    )
}

@Composable
fun MyStatelessItem(id: Int, mViewModel: MyViewModel)
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
                Text(mViewModel.items[id].title)
                Text(mViewModel.items[id].description)
                Text(mViewModel.order[id].toString())
            }

            ElevatedButton(onClick = {
                mViewModel.add(id)
            }
            )
            {
                Text("Add one")
            }
            ElevatedButton(onClick = {
                mViewModel.sub(id)
            })
            {
                Text("Sub one")
            }
        }
    }
}
