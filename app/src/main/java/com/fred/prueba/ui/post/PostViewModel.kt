package com.fred.prueba.ui.post

import androidx.lifecycle.ViewModel
import com.fred.prueba.models.Leagues

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    suspend fun fetchPosts(id: String) = repository.getTeams(id)

    suspend fun savePosts(leagues: List<Leagues>) = repository.savePosts(leagues)

    suspend fun readPost(isRead: Boolean, id: Int) = repository.readPost(isRead, id)

    fun getPosts() = repository.getPosts()

    fun getAllPosts() = repository.getAllPosts()

    fun getPostFavorites() = repository.getFavorites()

    suspend fun deletePost(id: Int) = repository.deletePost(id)

    suspend fun deleteAllPosts() = repository.deleteAllPosts()

}