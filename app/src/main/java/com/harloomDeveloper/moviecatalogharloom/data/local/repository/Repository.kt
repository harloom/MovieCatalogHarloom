package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

interface MovieRepository {
    fun getMovies(): LiveData<List<EMovie>>?
    fun setMovie(model: EMovie)
    suspend fun setMovieBG(data: EMovie)
    fun delete( model: EMovie)
}

interface  TvRepository{
    fun getTv(): LiveData<List<ETv>>?
    fun setTv(model: ETv)
    suspend fun setTvBG(data: ETv)
    fun delete( model: ETv)
}