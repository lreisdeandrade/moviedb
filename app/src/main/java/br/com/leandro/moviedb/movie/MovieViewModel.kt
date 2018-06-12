package br.com.leandro.moviedb.movie

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import br.com.leandro.moviedb.base.BaseViewModel
import br.com.leandro.moviedb.util.scheduler.BaseSchedulerProvider
import br.com.leandro.moviedbservice.model.MovieDetail
import br.com.leandro.moviedbservice.movie.MovieDataSource
import timber.log.Timber

/**
 * Created by leandro on 11/06/2018
 */

internal class MovieViewModel(application: Application, private val movieDataSource: MovieDataSource,
                              private val scheduler: BaseSchedulerProvider)
    : BaseViewModel(application) {

    internal val isLoadingLive: MutableLiveData<Boolean> = MutableLiveData()
    internal val hasErrorLive: MutableLiveData<Boolean> = MutableLiveData()
    internal val movieLive: MutableLiveData<MovieDetail> = MutableLiveData()

    fun start() {
        //UNUSED
    }

    fun loadMovieDetail(movieId : Int) {
        isLoadingLive.postValue(true)
        hasErrorLive.postValue(false)

        movieDataSource.getMovieDetail(movieId)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    isLoadingLive.postValue(false)
                    movieLive.postValue(it)
                }, {
                    isLoadingLive.postValue(false)
                    hasErrorLive.postValue(true)

                    Timber.e(it, "loadGenres: %s", it.message)
                })
    }

}