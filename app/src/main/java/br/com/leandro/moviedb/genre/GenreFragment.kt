package br.com.leandro.moviedb.genre

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.leandro.moviedb.AppContext
import br.com.leandro.moviedb.R
import br.com.leandro.moviedb.util.obtainViewModel
import br.com.leandro.moviedb.util.requiredBundleNotFound
import br.com.leandro.moviedbservice.model.Genre
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import kotlinx.android.synthetic.main.fragment_genre.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by leandro on 08/06/2018
 */

private const val EXTRA_GENRE = "EXTRA_GENRE"

class GenreFragment : Fragment() {

    private lateinit var viewModel: GenreViewModel
    private lateinit var genre: Genre
    private lateinit var moviesAdapter: MoviesByGenreAdapter

    companion object {
        fun newInstance(genre: Genre): GenreFragment {
            val fragment = GenreFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(EXTRA_GENRE, genre)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initViews()
        initViewModel()
    }

    private fun initData() {

        arguments?.getParcelable<Genre>(EXTRA_GENRE)?.let {
            genre = it
        } ?: run {
            activity?.requiredBundleNotFound(EXTRA_GENRE)
        }
    }

    private fun initViewModel() {
        viewModel = obtainViewModel(AppContext.instance, GenreViewModel::class.java)

        with(viewModel) {
            hasErrorLive.observe(this@GenreFragment, Observer {
                when (it) {
                    true -> {

                    }
                }
            })

            moviesByGenreLive.observe(this@GenreFragment, Observer {
                it?.let {
                    setupMoviesByGenreAdapter(it)
                }
            })
        }

        viewModel.loadMoviesByGenre(genre.id)
    }

    private fun setupMoviesByGenreAdapter(movieByGenreResponse: MovieByGenreResponse) {

        moviesRecycler.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        moviesRecycler.layoutManager = linearLayoutManager
        moviesRecycler.isNestedScrollingEnabled = false

        moviesRecycler.adapter = MoviesByGenreAdapter(movieByGenreResponse.results, {
            toast(it.title)
        })
    }

    private fun initViews() {
        genreName.text = genre.name
    }
}
