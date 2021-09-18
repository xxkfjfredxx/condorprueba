package com.fred.prueba.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fred.prueba.models.Leagues


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllPosts(leagues:List<Leagues>)

    @Query("SELECT * FROM leagues")
    fun getPosts():LiveData<List<Leagues>>

    @Query("UPDATE leagues set isRead=:isRead where id=:id")
    suspend fun readPost(isRead:Boolean, id:Int)

    @Query("Update leagues set isFavorite=:isFavorite where id=:id")
    suspend fun setFavorite(isFavorite:Boolean, id:Int)

    @Query("DELETE from leagues where id=:id")
    suspend fun deletePost(id: Int)

    @Query("DELETE from leagues")
    suspend fun deleteAllPosts()

    @Query("Select * from leagues where isFavorite=:isFavorite")
    fun getFavoritePosts(isFavorite: Boolean = true):LiveData<List<Leagues>>

    @Query("select * from leagues")
    fun getAllPosts():LiveData<List<Leagues>>

}