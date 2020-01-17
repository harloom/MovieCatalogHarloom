package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.local.dao.TvDao
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TvRepositoryImp (application: Application) : CoroutineScope ,  TvRepository {
    override suspend fun isFavoirt(id: Int): Int? {
        return  mDao?.isFavorit(id)
    }

    override suspend fun deleteById(id: Int) {
        mDao?.deleteById(id)
    }

    override suspend fun delete(model: ETv) {
        mDao?.delete(model)
    }

    override suspend fun setTvBG(data: ETv) {
        withContext(Dispatchers.IO){
            mDao?.set(data)
        }
    }

    override fun getTv() =  mDao?.getAll()

    override suspend fun  setTv(model: ETv) {
        launch  { setTvBG(model) }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var mDao: TvDao? =null

    init {
        val db = AppDatabase.getDatabase(application)
        mDao = db?.tvDao()
    }






}