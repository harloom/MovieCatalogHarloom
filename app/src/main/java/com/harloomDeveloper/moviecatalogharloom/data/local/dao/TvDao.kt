package com.harloomDeveloper.moviecatalogharloom.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.harloomDeveloper.moviecatalogharloom.data.local.entity.ETv
import com.harloomDeveloper.moviecatalogharloom.data.models.movie.ResultMovie
import com.harloomDeveloper.moviecatalogharloom.data.models.tv.ResultTv
import io.reactivex.Single

@Dao
interface TvDao {
    @Query("SELECT COUNT(*) from tv_table  WHERE id = :id")
    suspend fun isFavorit(id : Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set (movie: ETv)

    @Query("SELECT * from tv_table ORDER BY originalName ASC")
    fun getAll() : LiveData<List<ETv>>

    @Query("DELETE FROM tv_table")
    fun deleteAll()

    @Delete
    fun delete(movie: ETv)

    @Query("Delete from tv_table WHERE id = :id")
    fun deleteById(id : Int)

}