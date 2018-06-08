package br.com.leandro.moviedbservice.movie.remote

import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import br.com.leandro.moviedbservice.model.MovieDetail
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by leandro on 08/06/2018
 */

internal interface MovieService {

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Single<MovieDetail>
}