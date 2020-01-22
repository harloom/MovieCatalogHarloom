package com.harloomDeveloper.moviecatalogharloom

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.harloomDeveloper.moviecatalogharloom.ui.favorit.FavoritActivity
import com.harloomDeveloper.moviecatalogharloom.utils.ReminderReciver
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    companion object{
            private const val JOB_SCHEDULAR = 500

    }

    private lateinit var reminderReciver : ReminderReciver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)
        reminderReciver = ReminderReciver()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie, R.id.navigation_tv
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        flt_favorit.setOnClickListener {
            startActivity(Intent(this@MenuActivity,FavoritActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onStart() {
        super.onStart()
        reminderReciver.setRepeatingReminderDaily(this@MenuActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_change_language->{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            } else -> super.onOptionsItemSelected(item)
        }


    }



}
