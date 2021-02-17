package com.panjikrisnayasa.movietheaterapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.movietheaterapp.DetailViewModel.Companion.EXTRA_MOVIE_ID

class MoviesAdapter(private val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    companion object {
        const val POSTER_URL = "https://image.tmdb.org/t/p/w185"
    }

    private val mData = ArrayList<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun setData(movies: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(movies)
        notifyDataSetChanged()
    }

    private fun moveToDetail(movieId: String?) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra(EXTRA_MOVIE_ID, movieId)
        context.startActivity(detailIntent)
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                val poster: ImageView = findViewById(R.id.image_item_movie_poster)
                val title: TextView = findViewById(R.id.text_item_movie_title)
                val genre: TextView = findViewById(R.id.text_item_movie_genre)
                val release: TextView = findViewById(R.id.text_item_movie_release)
                val voteAverage: TextView = findViewById(R.id.text_item_movie_vote_average)
                poster.clipToOutline = true

                val posterUrl = POSTER_URL + movie.poster
                Glide.with(context).load(posterUrl).into(poster)
                title.text = movie.title
                genre.text = movie.genre
                release.text = movie.release
                voteAverage.text = movie.voteAverage

                itemView.setOnClickListener {
                    Log.d("hyp", "itemView tapped")
                    Log.d("hyp", "movie.id = ${movie.id}")
                    moveToDetail(movie.id)
                }
            }
        }
    }
}