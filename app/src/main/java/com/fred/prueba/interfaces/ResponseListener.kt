package com.fred.prueba.interfaces

import kotlinx.coroutines.Job

interface ResponseListener {
    fun onStarted()
    fun onFailure(message:String):Job
    fun onSuccess()
}