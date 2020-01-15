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

class TvRepositoryImp (application: Application) : CoroutineScope ,  TvRepository {
    override fun delete(model: ResultTv) {
        mDao?.delete(model)
    }

    override suspend fun setTvBG(data: ResultTv) {
        withContext(Dispatchers.IO){
            mDao?.set(data)
        }
    }

    override fun getTv() =  mDao?.getAll()

    override fun setTv(model: ResultTv) {
        launch  { setTvBG(model) }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mDao: TvDao?

    init {
        val db = CatalogDatabase.getDatabase(application)
        mDao = db?.tvDao()
    }






}