package br.com.leandro.moviedb.util

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import br.com.leandro.moviedb.ViewModelFactory
import timber.log.Timber

/**
 * Created by leandro on 08/06/18 for app-android.
 */

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, @IdRes containerViewId: Int) {
    supportFragmentManager.transact {
        add(containerViewId, fragment)
    }
}

fun AppCompatActivity.addFragmentToActivityWithBackStack(fragment: Fragment,
                                                         backStackFragment: Fragment,
                                                         @IdRes containerViewId: Int) {
    supportFragmentManager.transact {
        replace(containerViewId, fragment)
        addToBackStack(backStackFragment.id.toString())
    }
}

fun AppCompatActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}

fun Activity.requiredBundleNotFound(bundleNotFoundName: String) {
    Timber.e("Bundle %s is required and was not found", bundleNotFoundName)
    finish()
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}