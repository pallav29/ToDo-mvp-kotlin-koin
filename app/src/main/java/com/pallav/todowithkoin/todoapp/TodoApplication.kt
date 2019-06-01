package com.pallav.todowithkoin.todoapp

import android.app.Application
import com.pallav.todowithkoin.todoapp.di.todoAppModules
import org.koin.android.ext.android.startKoin

/**
 * Todo Application - main class
 *
 * Just run Koin context with startAndroidContext()
 *
 */
class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin(this, todoAppModules)
    }
}