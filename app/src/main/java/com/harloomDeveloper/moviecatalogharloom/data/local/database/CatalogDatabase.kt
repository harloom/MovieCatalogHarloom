package com.harloomDeveloper.moviecatalogharloom.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.TvDao
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

@Database(entities = [ResultMovie::class , ResultTv::class] ,version = 1 , exportSchema = false)
abstract  class CatalogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao() : TvDao

    //single
    companion object{
        const val DATABASE_NAME = "catalogDatabase"
        const val MOVIE_TABLE ="movie_table"
        const val  TV_TABLE = "tv_table"
        @Volatile
        private var INSTANCE: CatalogDatabase? = null

        fun getDatabase(context: Context): CatalogDatabase? {
            if (INSTANCE == null) {
                synchronized(CatalogDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CatalogDatabase::class.java, DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}