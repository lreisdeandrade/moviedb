package br.com.leandro.moviedbservice.model

/**
 * Created by leandro on 07/06/2018
 */

data class Movie(val adult: Boolean, val backdrop_path: Any, val genre_ids: List<Int>, val id: Int,
                 val original_language: String, val original_title: String, val overview: String,
                 val release_date: String, val poster_path: Any, val popularity: Double, val title: String,
                 val video: Boolean, val vote_average: Double, val vote_count: Int)