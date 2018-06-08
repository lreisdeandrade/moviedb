package br.com.leandro.moviedbservice.genre.remote

import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by leandro on 08/06/2018
 */

internal interface GenreService {

    @GET("genre/movie/list?&sort_by=created_at.asc")
    fun getGenres(): Single<GenreResponse>

    @GET("genre/{genre_id}/movies?&sort_by=created_at.asc")
    fun getMoviesByGenre(@Path("genre_id") genreId: Int): Single<MovieByGenreResponse>

}