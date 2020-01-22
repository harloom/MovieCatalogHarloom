package com.harloomDeveloper.moviecatalogharloom

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.harloomDeveloper.moviecatalogharloom.Utils.createNotificationChannel
import com.harloomDeveloper.moviecatalogharloom.utils.ReminderReciver


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this@MainApp)

    }



}