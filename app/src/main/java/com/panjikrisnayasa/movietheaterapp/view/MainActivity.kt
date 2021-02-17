package com.panjikrisnayasa.movietheaterapp.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.panjikrisnayasa.movietheaterapp.viewmodel.MainViewModel
import com.panjikrisnayasa.movietheaterapp.adapter.MoviesAdapter
import com.panjikrisnayasa.movietheaterapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MoviesAdapter
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findView()
        setUpRecyclerView()
        setUpViewModel()

        mViewModel.setMovies()
        mViewModel.getMovies().observe(this, Observer {
            if (it != null) {
                mAdapter.setData(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = MoviesAdapter(this)
        mAdapter.notifyDataSetChanged()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
    }

    private fun findView() {
        mRecyclerView = findViewById(R.id.recycler_main_movies)
        mProgressBar = findViewById(R.id.progress_main)
    }
}