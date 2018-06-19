package br.com.leandro.moviedb.util

import br.com.leandro.moviedbservice.MoviedbModule
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type

/**
 * Created by leandro on 19/06/2018
 */
fun <T> Any.parseObject(jsonFile: String, objectType: Type): T {
    val characterRaw = javaClass.classLoader.getResourceAsStream(jsonFile)
    val characterResponseJson = BufferedReader(InputStreamReader(characterRaw))

    return MoviedbModule.gsonBuilder().fromJson(characterResponseJson, objectType as Class<T>)
}
