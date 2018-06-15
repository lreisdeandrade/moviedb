package br.com.leandro.moviedb.util

/**
 * Created by leandro on 15/06/2018
 */
enum class Control { BREAK, CONTINUE }
fun <T> List<T>.each(consumer : (T) -> Control) : Boolean {
    for( t in this) {
        when(consumer(t)) {
            Control.BREAK -> return false
        }
    }
    return true
}