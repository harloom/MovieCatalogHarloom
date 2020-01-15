package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie

interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(movie: ResultMovie)

    @Query("SELECT * from movie_table ORDER BY originalTitle ASC")
    fun get() : LiveData<List<ResultMovie>>

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Delete
    fun delete(movie: ResultMovie)
}