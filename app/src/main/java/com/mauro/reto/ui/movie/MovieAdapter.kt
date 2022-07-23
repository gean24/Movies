package com.mauro.reto.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauro.reto.R
import com.mauro.reto.storage.entity.MovieDb
import java.util.*

class MovieAdapter(val clickListener: (dataModel: MovieDb?) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mList: MutableList<MovieDb>?
    private var isLoadingAdded = false
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        this.context = parent.context
        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.row_movie, parent, false)
                viewHolder = ViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.loading_dialog, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val viewHolder = holder as ViewHolder
                viewHolder.title.setText(item.title)
                viewHolder.des.setText(item.overview)
                if(!item.poster_path.isEmpty()) {
                    Glide.with(context)
                        //.load("https://image.tmdb.org/t/p/w500/" + item.poster_path)
                        .load("https://image.tmdb.org/t/p/w500/" + item.poster_path)
                        .error(R.drawable.ic_launcher_background)
                        .apply(RequestOptions.centerCropTransform())
                        .into(viewHolder.img)
                }
                viewHolder.content.setOnClickListener {
                    clickListener(item)
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = mList!!.size - 1
        if(position>=0){
            val result = getItem(position)
            if (result != null) {
                mList!!.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun add(movie: MovieDb) {
        mList!!.add(movie)
        notifyItemInserted(mList!!.size - 1)
    }

    fun addAll(moveResults: List<MovieDb>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getItem(position: Int): MovieDb {
        return mList!![position]
    }

    fun clearData() {
        mList!!.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById<View>(R.id.movie_title) as TextView
        val des: TextView = itemView.findViewById<View>(R.id.movie_desc) as TextView
        val img: ImageView = itemView.findViewById<View>(R.id.movie_poster) as ImageView
        val content: ConstraintLayout = itemView.findViewById<View>(R.id.content) as ConstraintLayout

    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById<View>(R.id.loadmore_progress) as ProgressBar

    }

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

    init {
        //this.context = context
        mList = LinkedList()
    }
}