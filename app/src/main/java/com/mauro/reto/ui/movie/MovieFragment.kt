package com.mauro.reto.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mauro.reto.R
import com.mauro.reto.core.ui.Result
import com.mauro.reto.core.ui.base.BaseFragment
import com.mauro.reto.core.utils.snackbar
import com.mauro.reto.storage.entity.MovieDb
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : BaseFragment() {
    companion object {
        fun newInstance() = MovieFragment()
        const val ARG_LOCAL_ID = "args_local_id"
    }
    private val movieViewModel: MovieViewModel by activityViewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentPage: Int = 1
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    private val mAdapter = MovieAdapter {
        val bundle = Bundle()
        bundle.putInt(ARG_LOCAL_ID, it!!.localId)
        addFragmentArg(MovieDetailFragment(),"MovieFragmentAttach",bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_movie,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        configAdapter()

        val loading = movieViewModel.loading

        val listMovie = movieViewModel.listMovies

        loading.value = true
        listMovie.value = null
        movieViewModel.getMovies(currentPage,isNetworkAvailable())

        movieViewModel.ldMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Result.Success -> {
                    if (loading.value) {
                        listMovie.value = state.data
                        loading.value = false
                        loadData(listMovie.value!!)
                    }
                }
                is Result.Loading -> {
                    listMovie.value = emptyList()
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

    private fun loadData(value: List<MovieDb>) {
        //configAdapter()
        mAdapter.removeLoadingFooter()
        isLoading = false
        mAdapter.addAll(value)
        if (currentPage < value.first().total_page) mAdapter.addLoadingFooter() else isLastPage =
            true
    }

    private fun configAdapter(){
        rvItems.layoutManager = linearLayoutManager
        rvItems.adapter = mAdapter

        rvItems.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                movieViewModel.getMovies(currentPage,isNetworkAvailable())
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }
}



abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}