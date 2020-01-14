package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv

interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set (movie: ResultTv)

    @Query("SELECT * from tv_table ORDER BY originalName ASC")
    fun getAll() : LiveData<List<ResultTv>>

    @Query("DELETE FROM tv_table")
    fun deleteAll()
}