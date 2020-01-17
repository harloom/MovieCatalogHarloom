package com.harloomDeveloper.moviecatalogharloom.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.harloomDeveloper.moviecatalogharloom.data.local.database.AppDatabase
import kotlinx.android.parcel.Parcelize


@Entity(tableName = AppDatabase.TV_TABLE)
@Parcelize
data class ETv(
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String?,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "originalLanguage")
    val originalLanguage: String?,
    @ColumnInfo(name = "originalName")
    val originalName: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @ColumnInfo(name = "posterPath")
    val posterPath: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double?,
    @ColumnInfo(name = "voteCount")
    val voteCount: Int?,
    @ColumnInfo(name = "firstAirDate")
    val firstAirDate : String?
) : Parcelable