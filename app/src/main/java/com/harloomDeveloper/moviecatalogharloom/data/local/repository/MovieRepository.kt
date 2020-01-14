package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import android.app.Application
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.CatalogDatabase
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieRepository ( application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var messageDao: MovieDao?

    init {
        val db = CatalogDatabase.getDatabase(application)
        messageDao = db?.movieDao()
    }

    fun getMovies() = messageDao?.get()

    fun setMovie(data: ResultMovie) {
        launch  { setMovieBG(data) }
    }

    private suspend fun setMovieBG(data: ResultMovie){
        withContext(Dispatchers.IO){
            messageDao?.set(data)
        }
    }
}