package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import android.app.Application
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.TvDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.CatalogDatabase
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TvRepository (application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mDao: TvDao?

    init {
        val db = CatalogDatabase.getDatabase(application)
        mDao = db?.tvDao()
    }

    fun getTvs() = mDao?.getAll()

    fun setMovie(data: ResultTv) {
        launch  { setTvBG(data) }
    }

    private suspend fun setTvBG(data: ResultTv){
        withContext(Dispatchers.IO){
            mDao?.set(data)
        }
    }
}