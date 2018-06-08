package br.com.leandro.moviedbservice.model

/**
 * Created by leandro on 07/06/2018
 */

data class MovieByGenreResponse(val id: Int, val page: Int, val results: List<Movie>, val total_pages: Int,
                                val total_results: Int)

