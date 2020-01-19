package com.harloomDeveloper.moviecatalogharloom

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings.Global.getString
import androidx.core.content.ContextCompat.getSystemService

object Utils {
   const val  KEY_MOVIE ="moviesIntent"
   const val  KEY_TvShow ="tvIntent"
   const val KEY_STATE_ITEM="bundle"
    const val StateQuerySearch = "state_text_search"


    fun createNotificationChannel(context: Context) {
        val CHANNEL_ID =context.getString(R.string.channel_id_reminder)
      // Create the NotificationChannel, but only on API 26+ because
      // the NotificationChannel class is new and not in the support library
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val name = context.getString(R.string.channel_reminder)
         val descriptionText =context.getString(R.string.channel_description)
         val importance = NotificationManager.IMPORTANCE_DEFAULT
         val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
         }
         // Register the channel with the system
         val notificationManager: NotificationManager =
           context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         notificationManager.createNotificationChannel(channel)
      }
   }

}