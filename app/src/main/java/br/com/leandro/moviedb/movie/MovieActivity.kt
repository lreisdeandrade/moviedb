package br.com.leandro.moviedb.movie

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.loadUrl
import br.com.leandro.moviedb.util.obtainViewModel
import br.com.leandro.moviedb.util.requiredBundleNotFound
import br.com.leandro.moviedbservice.model.MovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.startActivity
import timber.log.Timber

/**
 * Created by leandro on 11/06/2018
 */

private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
private const val posterUrl = "http://image.tmdb.org/t/p/original/"

class MovieActivity : AppCompatActivity() {

    private var defaultMovieId: Int = -1
    private var movieId: Int = defaultMovieId
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieDetail: MovieDetail

    companion object {
        @JvmStatic
        fun createIntent(context: Context, movieId: Int) {
            context.startActivity<MovieActivity>(
                    EXTRA_MOVIE_ID to movieId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initData()
        initViews()
        initViewModel()
    }

    private fun init() {
        //UNUSED
    }

    private fun initData() {
        with(intent) {
            getIntExtra(EXTRA_MOVIE_ID, defaultMovieId).let {
                when (it != defaultMovieId) {
                    true -> movieId = it
                    false -> requiredBundleNotFound(EXTRA_MOVIE_ID)
                }
            }
        }
    }

    private fun initViews() {
        setContentView(R.layout.activity_movie_detail)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(MovieViewModel::class.java)

        viewModel.apply {
            hasErrorLive.observe(this@MovieActivity, Observer {
                when (it) {
                    true -> {
                        showError()
                    }
                }
            })

            isLoadingLive.observe(this@MovieActivity, Observer {
                it?.let {

                } ?: run {

                }
            })

            movieLive.observe(this@MovieActivity, Observer {
                it?.let {
                    movieDetail = it
                    setupMovieInfo()
                } ?: run {

                }
            })

            viewModel.loadMovieDetail(movieId.toInt())

        }
    }

    private fun setupMovieInfo() {

        movieTitleView.text = movieDetail.title
        movieInfoView.text = movieDetail.releaseDate.substring(0, 4)
                .plus(" • ")
                .plus(movieDetail.runtime)
                .plus(" min")
                .plus(" • ")
                .plus(movieDetail.voteAverage)

        movieSinopseView.text = movieDetail.overview
        movieCoverView.loadUrl(posterUrl.plus(movieDetail.backdropPath))
    }

    private fun showError() {

        Timber.d("Erro carregar detalhe do filme")
    }
}
