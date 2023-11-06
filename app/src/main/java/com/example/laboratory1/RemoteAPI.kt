package com.example.laboratory1

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RemoteAPI {
    @GET("/")
    suspend fun get():JsonObject

    @POST("/")
    suspend fun submit(@Query("order") s: String):String
}

object HelperClass {
    fun getIstance(): RemoteAPI {

        val mRemoteCloudService =
            Retrofit.Builder().baseUrl("https://robertoberaldi.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                // we need to add converter factory to
                // convert JSON object to Java object
                .build().create(RemoteAPI::class.java)


        return mRemoteCloudService
    }
}
