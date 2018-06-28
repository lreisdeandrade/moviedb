package br.com.leandro.moviedbservice.movie

import br.com.leandro.moviedbservice.MoviedbDataSource
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import br.com.leandro.moviedbservice.model.MovieDetail
import io.reactivex.Single

/**
 * Created by leandro on 07/06/2018
 */

interface MovieDataSource : MoviedbDataSource {
    fun getMovieDetail(movieId: Long): Single<MovieDetail>
}
