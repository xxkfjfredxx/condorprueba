package com.fred.prueba.ui.post

import com.fred.prueba.data.AppDatabase
import com.fred.prueba.models.Leagues
import com.fred.prueba.network.ApiService
import com.fred.prueba.network.SafeApiRequest

class PostRepository(
    private val apiService: ApiService,
    private val db:AppDatabase
) :SafeApiRequest() {


    internal suspend fun getTeams(id:String) = apiRequest { apiService.getTeams(id) }

    suspend fun savePosts(list: List<Leagues>) {
        db.dao().saveAllPosts(list);
    }

    suspend fun readPost(isRead:Boolean, id:Int) = db.dao().readPost(isRead, id);

    fun getPosts() = db.dao().getPosts()

    fun getAllPosts()= db.dao().getAllPosts();

    fun getFavorites() = db.dao().getFavoritePosts(true)

    suspend fun deletePost(id: Int) = db.dao().deletePost(id)

    suspend fun deleteAllPosts() = db.dao().deleteAllPosts()



}