package com.fred.prueba.ui.user

import androidx.lifecycle.ViewModel

class UserViewModel(
    private val repository: UserRepository
):ViewModel(){

    suspend fun getInfoTeams(id:String) = repository.getInfoTeams(id)
    suspend fun getEvents(id:String) = repository.getEvents(id)
    suspend fun setFavorite(isFavorite:Boolean,id:Int) = repository.setFavorite(isFavorite, id)
}