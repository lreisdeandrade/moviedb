package br.com.leandro.moviedb.genre

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import br.com.leandro.moviedb.base.BaseViewModel
import br.com.leandro.moviedb.util.scheduler.BaseSchedulerProvider
import br.com.leandro.moviedbservice.genre.GenreDataSource
import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import timber.log.Timber

/**
 * Created by leandro on 08/06/2018
 */

internal class GenreViewModel(application: Application, private val genreDataSource: GenreDataSource,
                              private val scheduler: BaseSchedulerProvider)
    : BaseViewModel(application) {

    internal val isLoadingLive: MutableLiveData<Boolean> = MutableLiveData()
    internal val hasErrorLive: MutableLiveData<Boolean> = MutableLiveData()
    internal val genresLive: MutableLiveData<GenreResponse> = MutableLiveData()
    internal val moviesByGenreLive: MutableLiveData<MovieByGenreResponse> = MutableLiveData()

    fun start() {
        //UNUSED
    }

    fun loadGenres() {
        isLoadingLive.postValue(true)
        hasErrorLive.postValue(false)

        genreDataSource.getGenres()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    isLoadingLive.postValue(false)
                    genresLive.postValue(it)
                }, {
                    isLoadingLive.postValue(false)
                    hasErrorLive.postValue(true)

                    Timber.e(it, "loadGenres: %s", it.message)
                })
    }

    fun loadMoviesByGenre(genreId: Int) {
        isLoadingLive.postValue(true)
        hasErrorLive.postValue(false)

        genreDataSource.getMoviesByGenre(genreId)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    isLoadingLive.postValue(false)
                    moviesByGenreLive.postValue(it)
                }, {
                    isLoadingLive.postValue(false)
                    hasErrorLive.postValue(true)

                    Timber.e(it, "loadMoviesByGenre: %s", it.message)
                })
    }
}