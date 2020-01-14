package com.harloomDeveloper.moviecatalogharloom.data.models.tv


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.harloomDeveloper.moviecatalogharloom.data.local.database.CatalogDatabase.Companion.TV_TABLE
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TV_TABLE)
@Parcelize
data class ResultTv(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) : Parcelable