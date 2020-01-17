package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import io.reactivex.Single

interface MovieRepository {
    fun getMovies(): LiveData<List<EMovie>>?
    suspend fun  setMovie(model: EMovie)
    suspend fun setMovieBG(data: EMovie)
    suspend fun delete( model: EMovie)
    suspend fun deleteById(id : Int)
    suspend fun isFavoirt(id: Int): Int?
}

interface  TvRepository{
    fun getTv(): LiveData<List<ETv>>?
    suspend fun setTv(model: ETv)
    suspend fun setTvBG(data: ETv)
    suspend fun delete( model: ETv)
    suspend fun deleteById(id : Int)
    suspend fun isFavoirt(id: Int): Int?
}