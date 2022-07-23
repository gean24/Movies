package com.mauro.reto.api

import com.mauro.reto.api.response.BaseResponse
import com.mauro.reto.api.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("3/movie/upcoming")
    suspend fun initial(@Query("page") page:Int,@Query("api_key") api_key:String): Response<BaseResponse<List<MoviesResponse>>>

}