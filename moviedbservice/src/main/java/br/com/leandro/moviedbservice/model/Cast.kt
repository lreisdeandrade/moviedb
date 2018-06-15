package br.com.leandro.moviedbservice.model

import com.google.gson.annotations.SerializedName

/**
 * Created by leandro on 15/06/2018
 */
data class Cast(
        @SerializedName("cast_id")
        val castId: Int,
        val character: String,
        @SerializedName("credit_id")
        val credit_id: String,
        val gender: Int,
        val id: Int,
        val name: String,
        val order: Int,
        @SerializedName("profile_path")
        val profile_path: String
)