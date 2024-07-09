package com.srm.androidtendable

import android.app.Application
import com.srm.androidtendable.di.apiModule
import com.srm.androidtendable.di.appModule
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(apiModule, appModule)
        }
    }
}