package com.harloomDeveloper.moviecatalogharloom.data.local.repository

import androidx.lifecycle.LiveData
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

interface MovieRepository {
    fun getMovies(): LiveData<List<ResultMovie>>?
    fun setMovie(model: ResultMovie)
    suspend fun setMovieBG(data: ResultMovie)
    fun delete( model: ResultMovie)
}

interface  TvRepository{
    fun getTv(): LiveData<List<ResultTv>>?
    fun setTv(model: ResultTv)
    suspend fun setTvBG(data: ResultTv)
    fun delete( model: ResultTv)
}