package com.panjikrisnayasa.movietheaterapp.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.panjikrisnayasa.movietheaterapp.R
import com.panjikrisnayasa.movietheaterapp.adapter.MoviesAdapter
import com.panjikrisnayasa.movietheaterapp.model.Movie
import com.panjikrisnayasa.movietheaterapp.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var mPoster: ImageView
    private lateinit var mTitle: TextView
    private lateinit var mVoteAverage: TextView
    private lateinit var mVoteCount: TextView
    private lateinit var mGenre: TextView
    private lateinit var mRelease: TextView
    private lateinit var mRuntime: TextView
    private lateinit var mProduction: TextView
    private lateinit var mOverview: TextView
    private lateinit var mRating: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mViewBackground: View
    private lateinit var mViewModel: DetailViewModel
    private var mMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mMovie = intent?.getParcelableExtra(EXTRA_MOVIE)

        findView()
        showLoading(true)
        setUpToolbar()
        setUpViewModel()
        showDetailMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = mMovie?.title
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mViewBackground.visibility = View.VISIBLE
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.GONE
            mViewBackground.visibility = View.GONE
        }
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
    }

    private fun findView() {
        mPoster = findViewById(R.id.image_detail_poster)
        mTitle = findViewById(R.id.text_detail_title)
        mVoteAverage = findViewById(R.id.text_detail_vote_average)
        mVoteCount = findViewById(R.id.text_detail_vote_count)
        mGenre = findViewById(R.id.text_detail_genre)
        mRelease = findViewById(R.id.text_detail_release)
        mRuntime = findViewById(R.id.text_detail_runtime)
        mProduction = findViewById(R.id.text_detail_production)
        mOverview = findViewById(R.id.text_detail_overview)
        mRating = findViewById(R.id.text_detail_rating)
        mProgressBar = findViewById(R.id.progress_detail)
        mViewBackground = findViewById(R.id.view_detail_background)
    }

    private fun showDetailMovie() {
        val tMovieId = mMovie?.id
        if (tMovieId != null) {
            mViewModel.setDetailMovie(tMovieId)
            mViewModel.getDetailMovie().observe(this, {
                if (it != null) {
                    mPoster.clipToOutline = true
                    val posterUrl = MoviesAdapter.POSTER_URL + it.poster
                    Glide.with(this).load(posterUrl).into(mPoster)

                    mTitle.text = it.title
                    mVoteAverage.text = it.voteAverage
                    mVoteCount.text = String.format("(%s votes)", it.voteCount)
                    mGenre.text = it.genre
                    mRelease.text = changeDateFormat(it.release)
                    mRuntime.text = String.format("%s minutes", it.runtime)
                    mProduction.text = it.production
                    mOverview.text = it.overview

                    val tRating = it.rating
                    if (tRating != null) {
                        if (tRating) {
                            mRating.text = getString(R.string.main_text_adult_or_above)
                        } else {
                            mRating.text = getString(R.string.main_text_all_ages)
                        }
                    }
                    showLoading(false)
                }
            })
        }
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
}