package com.mauro.reto.api.response

import com.google.gson.annotations.SerializedName
import com.mauro.reto.storage.entity.MovieDb

data class MoviesResponse (
    //var localId: String = "",

    @SerializedName("adult")
    var adult: Boolean? = false,
    @SerializedName("backdrop_path")
    var backdrop_path: String? = "",
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("original_language")
    var original_language: String? = "",
    /*@SerializedName("original_title")
    var original_title: String,*/
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("popularity")
    var popularity: Double? = 0.0,
    @SerializedName("poster_path")
    var poster_path: String? = "",
    @SerializedName("release_date")
    var release_date: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("video")
    var video: Boolean? = false,
    @SerializedName("vote_average")
    var vote_average: Double? = 0.0,

    var page: Int = 0,
    var total_pages: Int = 0,

){
    fun toMovieDb() = MovieDb(
        //localId = localId,
        adult = adult!!,
        backdrop_path = backdrop_path?:"",
        id = id!!,
        original_language = original_language!!,
        //original_title = original_title,
        overview = overview?:"",
        popularity = popularity!!,
        poster_path = poster_path!!,
        release_date = release_date!!,
        title = title?:"",
        video = video!!,
        vote_average = vote_average!!,
        page = page,
        total_page = total_pages
    )
}