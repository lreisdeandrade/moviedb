package br.com.leandro.moviedb.util

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import br.com.leandro.moviedb.ViewModelFactory

/**
 * Created by leandro on 11/06/2018
 */

fun <T : ViewModel> Fragment.obtainViewModel(application: Application, viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)