package br.com.leandro.moviedbservice.genre

import br.com.leandro.moviedbservice.MoviedbDataSource
import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import io.reactivex.Single

/**
 * Created by leandro on 08/06/2018
 */

interface GenreDataSource : MoviedbDataSource {
    fun getGenres(): Single<GenreResponse>
    fun getMoviesByGenre(genreId: Int): Single<MovieByGenreResponse>
}
