package com.fred.prueba.interfaces

import com.fred.prueba.models.Leagues
import kotlinx.coroutines.Job

interface PostListener {
    fun onSelectedPost(leagues: Leagues, position:Int)
    fun onDeletePost(leagues: Leagues, position: Int):Job
}