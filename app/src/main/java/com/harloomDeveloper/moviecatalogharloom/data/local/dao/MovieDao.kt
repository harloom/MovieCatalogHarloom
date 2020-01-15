package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(movie: EMovie)

    @Query("SELECT * from movie_table ORDER BY originalTitle ASC")
    fun get() : LiveData<List<EMovie>>

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Delete
    fun delete(movie: EMovie)
}