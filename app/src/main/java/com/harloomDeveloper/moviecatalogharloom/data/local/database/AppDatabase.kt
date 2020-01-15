package com.harloomDeveloper.moviecatalogharloom.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.TvDao
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

@Database(entities = [ETv::class , EMovie::class] ,version = 2 , exportSchema = false)
abstract  class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao() : TvDao

    //single
    companion object{
        const val DATABASE_NAME = "catalogDatabase"
        const val MOVIE_TABLE ="movie_table"
        const val  TV_TABLE = "tv_table"


        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "catalogDatabase"
                        ).build()
                    }
                }
            return INSTANCE
        }
    }

}