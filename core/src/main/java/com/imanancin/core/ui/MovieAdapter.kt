package com.imanancin.core.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.imanancin.core.R
import com.imanancin.core.domain.model.Movie


class MovieAdapter(
    private val callback: (Movie) -> Unit
): PagingDataAdapter<Movie, MovieAdapter.MovieAdapterViewHolder>(object:
    DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

}) {


    class MovieAdapterViewHolder(view: View, val callback: (Movie) -> Unit) : RecyclerView.ViewHolder(view) {

         val title: TextView =  itemView.findViewById(R.id.movieTitle)
         val year: TextView =  itemView.findViewById(R.id.movieYear)
         val image: ImageView =  itemView.findViewById(R.id.moviePoster)

        fun bind(data: Movie?) {
            title.text = data?.title
            year.text = data?.release?.substring(0, 4)

            image.load("https://image.tmdb.org/t/p/original${data?.poster}") {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            itemView.setOnClickListener {
                if(data != null) {
                    callback(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieAdapterViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}