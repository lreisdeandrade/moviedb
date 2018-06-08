package br.com.leandro.moviedbservice.genre

import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import io.reactivex.Single

/**
 * Created by leandro on 08/06/2018
 */

class GenreRepository(private val genreDataSource: GenreDataSource) : GenreDataSource {
    override fun getGenres(): Single<GenreResponse> {
        return genreDataSource.getGenres()
    }

    override fun getMoviesByGenre(genreId: Int): Single<MovieByGenreResponse> {
        return genreDataSource.getMoviesByGenre(genreId)
    }
}