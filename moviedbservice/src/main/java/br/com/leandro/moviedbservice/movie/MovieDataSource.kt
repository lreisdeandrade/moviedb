package br.com.leandro.moviedbservice.movie

import br.com.leandro.moviedbservice.MoviedbDataSource
import io.reactivex.Single

/**
 * Created by leandro on 07/06/2018
 */

interface MovieDataSource : MoviedbDataSource {
    fun getGenres()
    fun getMoviesByGenre(genreId: Int)
    fun getMovieDetail(movieId: Int)
}
