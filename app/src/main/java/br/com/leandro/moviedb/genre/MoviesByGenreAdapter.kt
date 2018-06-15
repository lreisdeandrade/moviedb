package br.com.leandro.moviedb.genre

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.loadUrl
import br.com.leandro.moviedbservice.model.Movie
import kotlinx.android.synthetic.main.movie_by_genre_item.view.*

/**
 * Created by leandro on 11/06/2018
 */

private const val posterUrl = "http://image.tmdb.org/t/p/w185/"

class MoviesByGenreAdapter(private val items: List<Movie>, private val clickListener: (Movie) -> Unit) :
        RecyclerView.Adapter<MoviesByGenreAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_by_genre_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Movie, clickListener: (Movie) -> Unit) = with(itemView) {
            with(item) {
                movieCoverView.loadUrl(posterUrl.plus(item.poster_path))
                movieTitleView.text = item.title

                itemView.setOnClickListener { clickListener(item) }
            }
        }
    }
}