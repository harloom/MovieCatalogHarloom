package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.MovieDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieRepositoryImp (application: Application) : CoroutineScope , MovieRepository {
    override suspend fun isFavoirt(id: Int): Int? {
       return  mDao?.isFavorit(id)
    }

    override suspend fun deleteById(id: Int) {
        mDao?.deleteById(id)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mDao: MovieDao? =null

    init {
        val db = AppDatabase.getDatabase(application)
        mDao = db?.movieDao()
    }

    override fun getMovies(): LiveData<List<EMovie>>? {
       return mDao?.get()
    }

    override suspend fun setMovie(model: EMovie) {
        launch  { setMovieBG(model) }
    }

    override suspend fun setMovieBG(data: EMovie) {
        withContext(Dispatchers.IO) {
            mDao?.set(data)
        }
    }

    override  suspend fun delete(model: EMovie) {
         mDao?.delete(model)
    }


}