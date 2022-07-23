package com.mauro.reto.ui.movie

import android.graphics.Movie
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.mauro.reto.api.request.MovieRequest
import com.mauro.reto.domain.Repository
import com.mauro.reto.storage.entity.MovieDb
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mauro.reto.core.ui.Result
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    repository: Repository
) : ViewModel(){
    val loading = mutableStateOf(false)

    val listMovies:MutableState<List<MovieDb>?> = mutableStateOf(null)
    private val _ldMovies = MutableLiveData<MovieRequest>()
    val ldMovies: LiveData<Result<List<MovieDb>>> = _ldMovies.switchMap{
        repository.getMovies(it).asLiveData()
    }
    fun getMovies(page: Int, isNetworkAvailable: Boolean){
        _ldMovies.value = MovieRequest(
            page = page, isNetworkAvailable = isNetworkAvailable)
    }

    val movieDetail:MutableState<MovieDb?> = mutableStateOf(null)
    private val _ldMovieDetail = MutableLiveData<Int>()
    val ldMovieDetail: LiveData<Result<MovieDb>> = _ldMovieDetail.switchMap{
        repository.getMovieDetail(it).asLiveData()
    }
    fun getMovieDetail(localId: Int){
        _ldMovieDetail.value = localId
    }


}
