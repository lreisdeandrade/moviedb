package br.com.leandro.moviedbservice.model

import com.google.gson.annotations.SerializedName

/**
 * Created by leandro on 07/06/2018
 */

data class SpokenLanguage(@SerializedName("iso_639_1") val iso6391: String?, val name: String?)
