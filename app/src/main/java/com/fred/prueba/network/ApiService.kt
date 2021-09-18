package com.fred.prueba.network

import com.fred.prueba.interceptors.NetworkInterceptorConnection
import com.fred.prueba.models.DataEvents
import com.fred.prueba.models.DataPost
import com.fred.prueba.models.Event
import com.fred.prueba.models.Leagues
import com.fred.prueba.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("/api/v1/json/1/lookup_all_teams.php?")
    suspend fun getTeams(@Query("id") id:String):Response<DataPost>

    @GET("/api/v1/json/1/lookupteam.php?")
    suspend fun getInfoTeams(@Query("id") id:String):Response<DataPost>

    @GET("/api/v1/json/1/eventslast.php?")
    suspend fun getEvents(@Query("id") id:String):Response<DataEvents>

    companion object{
        operator fun invoke(
            networkInterceptorConnection: NetworkInterceptorConnection
        ):ApiService{
            val client = OkHttpClient()
                .newBuilder().readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(40,TimeUnit.SECONDS)
                .addInterceptor(networkInterceptorConnection)
                .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)
        }
    }

}