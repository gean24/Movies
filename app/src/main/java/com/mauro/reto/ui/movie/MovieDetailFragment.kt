package com.mauro.reto.ui.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauro.reto.R
import com.mauro.reto.core.ui.Result
import com.mauro.reto.core.ui.base.BaseFragment
import com.mauro.reto.core.utils.snackbar
import com.mauro.reto.storage.entity.MovieDb
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : BaseFragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()
    private var localId = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_movie_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            localId = it.getInt(MovieFragment.ARG_LOCAL_ID)
        }
    }

    private fun initUi() {
        val loading = movieViewModel.loading

        val movieDetail = movieViewModel.movieDetail

        loading.value = true
        movieDetail.value = null
        movieViewModel.getMovieDetail(localId)

        movieViewModel.ldMovieDetail.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Result.Success -> {
                    if (loading.value) {
                        movieDetail.value = state.data
                        loading.value = false
                        setData(movieDetail.value!!)
                    }
                }
                is Result.Loading -> {
                    movieDetail.value = null
                    loading.value = true
                }
                is Result.Error -> {
                    loading.value = false
                    state.exception.forEach {
                        activity?.snackbar("${it.message}")
                    }
                }
            }
        }
    }

    private fun setData(value: MovieDb) {
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/" + value.poster_path)
            .error(R.drawable.ic_launcher_background)
            .apply(RequestOptions.centerCropTransform())
            .into(movie_poster)
        movie_title.text = value.title
        movie_date.text = "Fecha: ${value.release_date}"
        movie_average.text = "Nota: ${value.vote_average}"
        movie_desc.text = value.overview
    }
}
