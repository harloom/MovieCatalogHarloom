package com.harloomDeveloper.moviecatalogharloom.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harloomDeveloper.moviecatalogharloom.R
import com.harloomDeveloper.moviecatalogharloom.data.api.Constant
import com.harloomDeveloper.moviecatalogharloom.data.api.NetworkBuilder
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReminderReciver : BroadcastReceiver() {
    companion object {
        const val TYPE_RELEASE = "ReleaseNotificationJob"
        const val TYPE_DAILY = "DailyNotificationJob"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101

    }

    override fun onReceive(context: Context?, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val title = if (type.equals(TYPE_RELEASE, ignoreCase = true)) TYPE_RELEASE else TYPE_DAILY
        val notifId = if (type.equals(TYPE_RELEASE, ignoreCase = true)) ID_DAILY else ID_RELEASE

        if (context != null) {
            showAlarmNotification(context,title,message,notifId)
            if(type.equals(TYPE_RELEASE,ignoreCase = true)){
                getReleaseToday(context)
            }
        }
    }
    private fun showAlarmNotification(context: Context, title: String, message: String?, notifId: Int) {
        val channelId =context.getString(R.string.channel_id_reminder)
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(alarmSound)
                .setChannelId(channelId)
        } else {
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSound(alarmSound)
                .setChannelId(channelId)
        }


        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)

    }

    fun setRepeatingReminderDaily(context: Context){
        val daily = context.resources.getString(R.string.key_daily)
        val release = context.resources.getString(R.string.key_release_today)
        val preference = PreferenceManager.getDefaultSharedPreferences(context);
        val reminderDaily = preference.getBoolean(daily,false)
        val reminderRelease = preference.getBoolean(release,false)
        if(reminderDaily){
            if(isAlarmSet(context,TYPE_DAILY)){
                return
            }
            setRepeatingDaily(context,TYPE_DAILY,"Ayo Cek Film Terbaru ^_^")
        }else{
            cancelAlarm(context,TYPE_DAILY)

        }

        if(reminderRelease){
            if(isAlarmSet(context,TYPE_RELEASE)){
                return
            }
            setRepeatinRelease(context, TYPE_RELEASE,"new Release")
        }else{
            cancelAlarm(context,TYPE_RELEASE)
        }

    }



    fun setRepeatinRelease(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReciver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        /* alarm 8:00 AM */
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,8)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)


    }

    fun setRepeatingDaily(context: Context, type: String, message: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReciver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        /* alarm 7:00 AM */
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,7 )
        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReciver::class.java)
        val requestCode = if (type.equals(TYPE_RELEASE, ignoreCase = true)) ID_RELEASE else ID_DAILY
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

    }

    fun isAlarmSet(context: Context, type: String): Boolean {
        val intent = Intent(context, ReminderReciver::class.java)
        val requestCode = if (type.equals(TYPE_RELEASE, ignoreCase = true)) ID_RELEASE else ID_DAILY

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null
    }


    private fun getReleaseToday(context: Context) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val dateFormat =  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val calendar = Calendar.getInstance()
                val date = dateFormat.format(calendar.time)
                val response = NetworkBuilder.apiService.discoverMovie(dateLte = date,dateGte = date)
                if(response.isSuccessful){
                    response.body()?.resultMovies?.forEachIndexed { index, resultMovie ->
                        showNotificationRelease(context,resultMovie,
                            200+index)
                    }

                }
            }catch (e : Exception){

                this.cancel(e.message.toString())

            }

        }

        job.invokeOnCompletion {
        }


    }

    private fun showNotificationRelease(context: Context, movie: ResultMovie, notifId: Int) {
        val channelId = context.getString(R.string.channel_id_release)
        val channelName = context.getString(R.string.channel_release)
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(movie.title)
                .setSmallIcon(R.drawable.ic_fiber_new)
                .setContentText(movie.releaseDate)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
        } else {
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(movie.title)
                .setSmallIcon(R.drawable.ic_fiber_new)
                .setContentText(movie.releaseDate)
                .setColor(ContextCompat.getColor(context, android.R.color.black))

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        try {
            Glide.with(context).asBitmap()
                .load(Constant.BASE_IMAGE+movie.posterPath).into(object:CustomTarget<Bitmap>(){
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {

                        builder.setLargeIcon(resource)
                        .setStyle(NotificationCompat.BigPictureStyle()
                        .bigPicture(resource)
                        .bigLargeIcon(null))
                        val notification = builder.build()
                        notificationManagerCompat.notify(notifId, notification)
                    }

                })


        }catch (e: Exception){

        }

    }

}

