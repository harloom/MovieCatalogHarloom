package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.EMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT COUNT(*) from movie_table  WHERE id = :id")
    suspend fun isFavorit(id : Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(movie: EMovie)

    @Query("SELECT * from movie_table ORDER BY originalTitle ASC")
    fun get() : LiveData<List<EMovie>>

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(movie: EMovie)

    @Query("Delete from movie_table WHERE id = :id")
    suspend fun deleteById(id : Int)
}