package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set (movie: ETv)

    @Query("SELECT * from tv_table ORDER BY originalTitle ASC")
    fun getAll() : LiveData<List<ETv>>

    @Query("DELETE FROM tv_table")
    fun deleteAll()

    @Delete
    fun delete(movie: ETv)
}