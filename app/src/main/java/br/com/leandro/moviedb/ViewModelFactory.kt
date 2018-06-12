package br.com.leandro.moviedb

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting
import br.com.leandro.moviedb.genre.GenreViewModel
import br.com.leandro.moviedb.movie.MovieViewModel
import br.com.leandro.moviedb.util.scheduler.SchedulerProvider


/**
 * Created by leandro on 08/06/2018
 */

class ViewModelFactory private constructor(private val application: AppContext) :
        ViewModelProvider.NewInstanceFactory() {

//    init {
//        DaggerInjector.getApplicationComponent().inject(this)
//    }

    override fun <T /**/ : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(GenreViewModel::class.java) -> {
                        GenreViewModel(application,
                                Injection.provideGenreRepository(), SchedulerProvider)
                    }
                    isAssignableFrom(MovieViewModel::class.java) -> {
                        MovieViewModel(application,
                                Injection.provideMovieRepository(), SchedulerProvider)
                    }

                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE
                ?: synchronized(ViewModelFactory::class.java) {
                    val viewModelFactory = INSTANCE ?: ViewModelFactory(application as AppContext)
                            .also { INSTANCE = it }
                    viewModelFactory
                }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
