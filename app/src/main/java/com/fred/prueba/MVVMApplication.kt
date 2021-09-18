package com.fred.prueba

import android.app.Application
import com.fred.prueba.data.AppDatabase
import com.fred.prueba.interceptors.NetworkInterceptorConnection
import com.fred.prueba.models.Leagues
import com.fred.prueba.network.ApiService
import com.fred.prueba.ui.post.PostRepository
import com.fred.prueba.ui.post.PostViewModelFactory
import com.fred.prueba.ui.user.UserRepository
import com.fred.prueba.ui.user.UserViewModelFactory
import com.fred.prueba.utils.BuildVersion
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*


class MVVMApplication:Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { Leagues() }

        bind() from singleton { BuildVersion() }

        bind() from singleton { AppDatabase(instance()) }

        bind() from singleton { NetworkInterceptorConnection(instance(), instance()) }

        bind() from singleton { ApiService(instance()) }

        //Todo dependency injection Post

        bind() from singleton { PostRepository(instance(), instance()) }
        bind() from provider { PostViewModelFactory(instance()) }


        //Todo dependency injection User
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { UserViewModelFactory(instance()) }

    }
}