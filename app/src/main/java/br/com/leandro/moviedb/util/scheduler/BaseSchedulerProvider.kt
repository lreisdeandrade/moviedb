package br.com.leandro.moviedb.util.scheduler

import io.reactivex.Scheduler

/**
 * Created by leandro on 08/06/2018
 */
interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
