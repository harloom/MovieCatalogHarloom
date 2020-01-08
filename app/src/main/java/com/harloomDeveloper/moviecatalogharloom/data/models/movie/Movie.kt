package com.harloomDeveloper.moviecatalogharloom.data.models.movie


import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("dates")
    val dates: Dates?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val resultMovies: List<ResultMovie>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int
)

object MovieEx{
    fun getExample() : Movie?{
        return null
    }
}