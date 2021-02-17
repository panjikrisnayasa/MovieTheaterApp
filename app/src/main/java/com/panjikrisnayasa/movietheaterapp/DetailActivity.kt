package com.panjikrisnayasa.movietheaterapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class DetailActivity : AppCompatActivity() {

    private lateinit var mPoster: ImageView
    private lateinit var mTitle: TextView
    private lateinit var mVoteAverage: TextView
    private lateinit var mVoteCount: TextView
    private lateinit var mGenre: TextView
    private lateinit var mRelease: TextView
    private lateinit var mRuntime: TextView
    private lateinit var mProduction: TextView
    private lateinit var mOverview: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setUpToolbar()
        setUpViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.GONE
        }
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
    }
}