package com.mauro.reto.api.request

data class MovieRequest (
    val page: Int,
    val isNetworkAvailable:Boolean = false,
)
