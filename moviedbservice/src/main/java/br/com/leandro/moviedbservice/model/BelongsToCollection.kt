package br.com.leandro.moviedbservice.model

import com.google.gson.annotations.SerializedName

/**
 * Created by leandro on 08/06/2018
 */

data class BelongsToCollection(val id: Int, val name: String,
                               @SerializedName("poster_path")
                               val posterPath: String,
                               @SerializedName("backdrop_path")
                               val backdropPath: String)