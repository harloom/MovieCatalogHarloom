package com.harloomDeveloper.moviecatalogharloom

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.harloomDeveloper.moviecatalogharloom.Utils.createNotificationChannel
import com.harloomDeveloper.moviecatalogharloom.utils.ReminderReciver
import com.harloomDeveloper.moviecatalogharloom.utils.ReminderReciver.Companion.TYPE_REPEATING

class MainApp : Application() {
    private lateinit var reminderReciver :ReminderReciver
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this@MainApp)
        reminderReciver = ReminderReciver()
        reminderReciver.setRepeatingReminderDaily(this@MainApp)

    }



}