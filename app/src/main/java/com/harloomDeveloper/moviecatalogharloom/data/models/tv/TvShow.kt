package com.harloomDeveloper.moviecatalogharloom.data.models.tv


import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val resultTv: List<ResultTv>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)