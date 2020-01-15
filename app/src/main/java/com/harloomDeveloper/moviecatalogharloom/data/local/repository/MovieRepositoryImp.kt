package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.CatalogDatabase
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImp (application: Application) : CoroutineScope , MovieRepository {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mDao: MovieDao?

    init {
        val db = CatalogDatabase.getDatabase(application)
        mDao = db?.movieDao()
    }

    override fun getMovies(): LiveData<List<ResultMovie>>? {
       return mDao?.get()
    }

    override fun setMovie(model: ResultMovie) {
        launch  { setMovieBG(model) }
    }

    override suspend fun setMovieBG(data: ResultMovie) {
        withContext(Dispatchers.IO) {
            mDao?.set(data)
        }
    }

    override fun delete(model: ResultMovie) {
         mDao?.delete(model)
    }


}