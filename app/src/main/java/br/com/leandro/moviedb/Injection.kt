package br.com.leandro.moviedb

import br.com.leandro.moviedbservice.genre.GenreDataSource
import br.com.leandro.moviedbservice.genre.GenreRepository
import br.com.leandro.moviedbservice.genre.remote.GenreApi
import br.com.leandro.moviedbservice.movie.MovieDataSource
import br.com.leandro.moviedbservice.movie.MovieRepository
import br.com.leandro.moviedbservice.movie.remote.MovieApi

/**
 * Created by leandro on 08/06/2018
 */

/**
 * Enables injection of mock implementations for
 * [CmsRepository] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
object Injection {

    fun provideGenreRepository(): GenreDataSource {
        return GenreRepository(GenreApi)
    }

    fun provideMovieRepository(): MovieDataSource {
        return MovieRepository(MovieApi)
    }
}
