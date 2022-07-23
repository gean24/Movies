package com.mauro.reto.api.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("results")
    val data: T,

    @SerializedName("total_pages")
    val total_pages: Int,

    @SerializedName("errors")
    val errors: List<String>? = null
)

/*data class BaseResponseError(
    @SerializedName("status")
    val status: Int = 0,

    @SerializedName("detail")
    val detail: String = "",

    @SerializedName("code")
    val code: String = "",
)*/