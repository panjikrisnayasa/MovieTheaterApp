package com.panjikrisnayasa.movietheaterapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    companion object {
        const val API_KEY = "cf6b5328dcc68b107a713f503eb6e1d1"
    }

    val listMovies = MutableLiveData<ArrayList<Movie>>()

    fun setMovies() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "http://api.themoviedb.org/3/movie/top_rated?limit=3&api_key=$API_KEY"

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
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val tMovie = list.getJSONObject(i)
                        val movie = Movie()

                        movie.id = tMovie.getString("id")
                        movie.poster = tMovie.getString("poster_path")
                        movie.title = tMovie.getString("title")
                        movie.voteAverage = tMovie.getDouble("vote_average").toString()
                        movie.release = tMovie.getString("release_date")

//                        val tGenre = tMovie.getJSONArray("genre_ids").getJSONObject(0)

                        listItems.add(movie)
                    }
                    listMovies.postValue(listItems)
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

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}