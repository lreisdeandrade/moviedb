package br.com.leandro.moviedbservice.movie

/**
 * Created by leandro on 07/06/2018
 */

class MovieRepository(private val movieDataSource: MovieDataSource) : MovieDataSource {
    override fun getGenres() {
        return movieDataSource.getGenres()
    }

    override fun getMoviesByGenre(genreId: Int) {
        return movieDataSource.getMoviesByGenre(genreId)
    }

    override fun getMovieDetail(movieId: Int) {
        return movieDataSource.getMovieDetail(movieId)
    }
}