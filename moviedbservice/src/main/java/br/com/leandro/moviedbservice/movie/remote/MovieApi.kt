package br.com.leandro.moviedbservice.movie.remote

import br.com.leandro.moviedbservice.MoviedbModule
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import br.com.leandro.moviedbservice.model.MovieDetail
import br.com.leandro.moviedbservice.movie.MovieDataSource
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by leandro on 08/06/2018
 */
object MovieApi : MovieDataSource {

    private val movieService: MovieService

    init {
        val retrofit = MoviedbModule.retrofit
        movieService = retrofit.create(MovieService::class.java)
    }



    override fun getMovieDetail(movieId: Int): Single<MovieDetail> {
        return movieService.getMovieDetail(movieId)
                .doOnError { Timber.e(it, "getMovieDetail: %s", it.message) }
    }
}