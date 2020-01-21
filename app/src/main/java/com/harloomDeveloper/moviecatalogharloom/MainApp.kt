package com.harloomDeveloper.moviecatalogharloom

import android.app.Application
import com.harloomDeveloper.moviecatalogharloom.Utils.createNotificationChannel

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        createNotificationChannel(this@MainApp)
    }
}