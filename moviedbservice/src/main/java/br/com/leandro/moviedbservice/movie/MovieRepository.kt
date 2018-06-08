package br.com.leandro.moviedbservice.movie

import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import br.com.leandro.moviedbservice.model.MovieDetail
import io.reactivex.Single

/**
 * Created by leandro on 07/06/2018
 */

class MovieRepository(private val movieDataSource: MovieDataSource) : MovieDataSource {

    override fun getMovieDetail(movieId: Int): Single<MovieDetail> {
        return movieDataSource.getMovieDetail(movieId)
    }
}