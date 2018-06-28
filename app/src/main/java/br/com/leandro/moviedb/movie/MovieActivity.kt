package br.com.leandro.moviedb.movie

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.leandro.moviedb.R
import br.com.leandro.moviedbservice.model.MovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.startActivity
import timber.log.Timber
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.view.Menu
import android.view.MenuItem
import br.com.leandro.moviedb.util.*
import kotlinx.android.synthetic.main.content_movie_detail.*

/**
 * Created by leandro on 11/06/2018
 */

private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
private const val DIRECTOR_QUERY = "Diretor"
private const val POSTER_URL = "http://image.tmdb.org/t/p/original/"

class MovieActivity : AppCompatActivity() {

    private lateinit var menu: Menu
    private lateinit var collapsingToolbar: CollapsingToolbarLayout

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.
        this.menu = menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        hideOption(R.id.action_settings)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
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

        setSupportActionBar(toolbar)
        collapsingToolbar = findViewById(R.id.toolbar_layout)

        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    showOption(R.id.action_settings)

                } else if (isShow) {
                    isShow = false
                    hideOption(R.id.action_settings)
                }
            }
        })
    }

    private fun hideOption(id: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val item = menu.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val item = menu.findItem(id)
        item.isVisible = true
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(MovieViewModel::class.java)

        viewModel.apply {
            hasErrorLive.observe(this@MovieActivity, Observer {
                when (it) {
                    true -> showError()
                }
            })

            isLoadingLive.observe(this@MovieActivity, Observer {
                when (it) {
                    true -> {
                        movieDetailLoadingView.visible()
                        fabMovie.gone()
                    }
                    false -> {
                        movieDetailLoadingView.gone()
                        fabMovie.visible()
                    }
                }
            })

            movieLive.observe(this@MovieActivity, Observer {
                it?.let {
                    movieDetail = it
                    setupMovieInfo()
                } ?: run {
                    //TODO HANDLE ERROR REQUEST
                }
            })

            viewModel.loadMovieDetail(movieId)
        }
    }

    private fun setupMovieInfo() {
        with(movieDetail) {
            movieYear.text = releaseDate.substring(0, 4)
            movieTimer.text = runtime.toString().plus(getString(R.string.min_label))
            movieRating.text = voteAverage.toString()
            movieSinopseView.text = overview
            collapsingToolbar.title = title

            movieGenres.text = formatGenresName().dropLast(2)
            movieDirector.text = findDirectorsName()
            movieCast.text = formatCastNames()

            moviePosterView.loadUrl(POSTER_URL.plus(backdropPath))
            movieBackDropView.loadUrl(POSTER_URL.plus(posterPath))
        }
    }

    private fun formatGenresName(): String {
        var genresName = ""
        movieDetail.genres.forEach {
            genresName += it.name.plus(" | ")
        }
        return genresName
    }

    private fun findDirectorsName(): String {
        var directorName = ""
        movieDetail.credits.crew.each {
            if (it.job == DIRECTOR_QUERY) {
                directorName = it.name
                Control.BREAK
            }
            Control.CONTINUE
        }
        return directorName
    }

    private fun formatCastNames(): String {
        var castsName = ""
        //Display only the 5 firsts actors ( Implement castDetailActivity ? )
        for (i in 0..4) {
            castsName += (movieDetail.credits.cast[i].name).plus(getString(R.string.comma))
        }
        return castsName
    }

    private fun showError() {
        Timber.d("Erro ao carregar detalhe do filme")
    }
}