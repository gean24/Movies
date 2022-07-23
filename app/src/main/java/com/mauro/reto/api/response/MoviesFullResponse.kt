package com.mauro.reto.api.response

import com.mauro.reto.storage.entity.MovieDb
import kotlinx.coroutines.flow.Flow
import java.util.*

data class MoviesFullResponse(
    var movies: Flow<List<MovieDb>>,
    var total_pages: Int,
)