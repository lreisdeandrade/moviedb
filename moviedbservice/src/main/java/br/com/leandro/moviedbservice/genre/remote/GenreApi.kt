package br.com.leandro.moviedbservice.genre.remote

import br.com.leandro.moviedbservice.MoviedbModule
import br.com.leandro.moviedbservice.genre.GenreDataSource
import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import io.reactivex.Single
import timber.log.Timber

/**
 * Created by leandro on 08/06/2018
 */

object GenreApi : GenreDataSource {

    private val genreService: GenreService

    init {
        val retrofit = MoviedbModule.retrofit
        genreService = retrofit.create(GenreService::class.java)
    }

    override fun getGenres(): Single<GenreResponse> {
        return genreService.getGenres()
                .doOnError { Timber.e(it, "getGenres: %s", it.message) }
    }

    override fun getMoviesByGenre(genreId: Int): Single<MovieByGenreResponse> {
        return genreService.getMoviesByGenre(genreId)
                .doOnError { Timber.e(it, "getMoviesByGenre: %s", it.message) }
    }
}