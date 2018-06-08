package br.com.leandro.moviedb

import android.app.Application
import br.com.leandro.moviedbservice.LoggingInterceptor
import br.com.leandro.moviedbservice.MoviedbApiEndpoint
import br.com.leandro.moviedbservice.MoviedbModule
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

/**
 * Created by leandro on 08/06/2018
 */

class AppContext : Application() {

    companion object {
        lateinit var instance: AppContext private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
        initializeTimber()
        initializeTimezone()
        initializeApiModules()
    }

    private fun initDagger() {
//        DaggerInjector.initializeApplicationComponent(this);
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeTimezone() {
        AndroidThreeTen.init(this)
    }


    private fun initializeApiModules() {
        MoviedbModule.setRetrofit(MoviedbApiEndpoint.PROD, LoggingInterceptor.Level.FULL)
    }
}