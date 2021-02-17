package com.panjikrisnayasa.movietheaterapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.movietheaterapp.model.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    val detailMovie = MutableLiveData<Movie>()

    fun setDetailMovie(movieId: String?) {
        val client = AsyncHttpClient()
        val url =
            "http://api.themoviedb.org/3/movie/$movieId?limit=3&api_key=${MainViewModel.API_KEY}"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    var result = ""
                    if (responseBody != null)
                        result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val movie = Movie()
                    movie.id = responseObject.getString("id")
                    movie.poster = responseObject.getString("poster_path")
                    movie.title = responseObject.getString("title")
                    movie.voteAverage = responseObject.getDouble("vote_average").toString()
                    movie.voteCount = responseObject.getInt("vote_count").toString()
                    movie.genre =
                        responseObject.getJSONArray("genres").getJSONObject(0).getString("name")
                    movie.release = responseObject.getString("release_date")
                    movie.runtime = responseObject.getInt("runtime").toString()
                    movie.production =
                        responseObject.getJSONArray("production_companies").getJSONObject(0)
                            .getString("name")
                    movie.overview = responseObject.getString("overview")
                    movie.rating = responseObject.getBoolean("adult")

                    detailMovie.postValue(movie)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                error?.printStackTrace()
            }
        })
    }

    fun getDetailMovie(): LiveData<Movie> {
        return detailMovie
    }
}