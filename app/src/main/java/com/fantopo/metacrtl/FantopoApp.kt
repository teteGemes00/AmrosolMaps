package com.fantopo.metacrtl

import android.app.Application
import com.fantopo.metacrtl.di.AppContainer

class FantopoApp : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
