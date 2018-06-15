package br.com.leandro.moviedbservice.model

import com.google.gson.annotations.SerializedName

/**
 * Created by leandro on 15/06/2018
 */

data class Crew(
        @SerializedName("credit_id")
        val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        val name: String,
        @SerializedName("profile_path")
        val profilePath: String
)