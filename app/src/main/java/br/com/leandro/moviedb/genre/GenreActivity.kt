package br.com.leandro.moviedb.genre

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.*
import br.com.leandro.moviedbservice.model.Genre
import kotlinx.android.synthetic.main.activity_genre.*
import org.jetbrains.anko.startActivity
import timber.log.Timber

class GenreActivity : AppCompatActivity() {

    private lateinit var genresList: List<Genre>
    private lateinit var viewModel: GenreViewModel

    companion object {
        @JvmStatic
        fun createIntent(context: Context) {
            context.startActivity<GenreActivity>()
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
        //UNUSED
    }

    private fun initViews() {
        setContentView(R.layout.activity_genre)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(GenreViewModel::class.java)

        viewModel.apply {
            hasErrorLive.observe(this@GenreActivity, Observer {
                when (it) {
                    true -> {
                        showError()
                    }
                }
            })

            isLoadingLive.observe(this@GenreActivity, Observer {
                when (it) {
                    true -> genresLoadingView.visible()
                    false -> genresLoadingView.gone()
                }
            })

            genresLive.observe(this@GenreActivity, Observer { genreReponse ->
                genreReponse?.let {
                    genresList = it.genres
                    setupContainerGenre()
                }
            })

            viewModel.loadGenres()
        }
    }

    private fun setupContainerGenre() {
        for (item: Genre in genresList) {
            addFragmentToActivity(GenreFragment.newInstance(item), R.id.genreView)
        }
    }

    private fun showError() {
        Timber.d("ERROR")
    }
}