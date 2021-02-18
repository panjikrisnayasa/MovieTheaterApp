package com.panjikrisnayasa.movietheaterapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.movietheaterapp.R
import com.panjikrisnayasa.movietheaterapp.model.Movie
import com.panjikrisnayasa.movietheaterapp.view.DetailActivity
import com.panjikrisnayasa.movietheaterapp.view.DetailActivity.Companion.EXTRA_MOVIE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val context: Context) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    companion object {
        const val POSTER_URL = "https://image.tmdb.org/t/p/w500"
    }

    private val mData = ArrayList<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun setData(movies: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(movies)
        notifyDataSetChanged()
    }

    private fun moveToDetail(movie: Movie) {
        val detailIntent = Intent(context, DetailActivity::class.java)
        detailIntent.putExtra(EXTRA_MOVIE, movie)
        context.startActivity(detailIntent)
    }

    private fun changeDateFormat(date: String?): String? {
        if (date != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val outputFormat = SimpleDateFormat("d MMM yyyy", Locale.US)
            val tDate = inputFormat.parse(date)
            if (tDate != null)
                return outputFormat.format(tDate)
        }
        return null
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                val poster: ImageView = findViewById(R.id.image_item_movie_poster)
                val title: TextView = findViewById(R.id.text_item_movie_title)
                val rating: TextView = findViewById(R.id.text_item_movie_rating)
                val release: TextView = findViewById(R.id.text_item_movie_release)
                val voteAverage: TextView = findViewById(R.id.text_item_movie_vote_average)
                poster.clipToOutline = true

                val posterUrl = POSTER_URL + movie.poster
                Glide.with(context).load(posterUrl).into(poster)
                title.text = movie.title

                val tRating = movie.rating
                if (tRating != null) {
                    if (tRating) {
                        rating.text = context.getString(R.string.main_text_adult_or_above)
                    } else {
                        rating.text = context.getString(R.string.main_text_all_ages)
                    }
                }

                release.text = changeDateFormat(movie.release)
                voteAverage.text = movie.voteAverage

                itemView.setOnClickListener {
                    moveToDetail(movie)
                }
            }
        }
    }
}