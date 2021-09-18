package com.fred.prueba.ui.user

import com.fred.prueba.data.AppDatabase
import com.fred.prueba.network.ApiService
import com.fred.prueba.network.SafeApiRequest

class UserRepository(
    private val apiService: ApiService,
    private val db:AppDatabase
):SafeApiRequest(){

    internal suspend fun getInfoTeams(id:String) = apiRequest { apiService.getInfoTeams(id) }

    internal suspend fun setFavorite(isFavorite:Boolean, id:Int) = db.dao().setFavorite(isFavorite, id)

    internal suspend fun getEvents(id:String) = apiRequest { apiService.getEvents(id) }

}