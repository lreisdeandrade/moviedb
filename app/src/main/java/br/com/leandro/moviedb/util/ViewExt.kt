package br.com.leandro.moviedb.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.leandro.moviedb.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by leandro on 11/06/2018
 */

fun ImageView.loadUrl(url: String) {
    Glide.with(context).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.img_movie_placeholder))
            .load(url).into(this)

}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}