package com.example.laboratory1

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratory1.Model.DataModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject


data class Item(
    var title:String="Title",
    var description:String="Description",
    var logo: Bitmap?=null)


class MyViewModel : ViewModel() {

    var n = 10 //Guess a number
    lateinit var order : MutableList<Int>// = List(n){0}.toMutableStateList()
    var msg by mutableStateOf("Submit Your order!!")
    lateinit var items : MutableList<Item>// = List(n){Item()}.toMutableList()
    var mRemoteService : RemoteAPI =HelperClass.getIstance()  //LocalDataSource()


     suspend fun getAll(){
         runBlocking {
             val json = mRemoteService.get()
             val keys= json.keySet()
             n = keys.size
             order = List(n){0}.toMutableStateList()
             items = List(n){Item()}.toMutableList()
             var i=0
             for (k in json.keySet()) {
                 Log.i("REPLY ALL", "" + json)
                 items[i].title=k
                 items[i].description=json.get(k).asString
                 i++
             }
         }
     }
    fun sub(i: Int) {
        if (order[i]>0) order[i]--
    }

    fun add(i :Int) {
        order[i]++
        }

    fun submit(){
        viewModelScope.launch {
            val json = Gson()
            val data = json.toJson(order)
            Log.i("SUBMIT",data)
            val r = mRemoteService.submit(data)
            msg="...Done!"+r
        }
    }

}