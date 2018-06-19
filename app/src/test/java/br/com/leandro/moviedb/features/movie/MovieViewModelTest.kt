package br.com.leandro.moviedb.features.movie

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.res.Resources
import br.com.leandro.moviedb.movie.MovieViewModel
import br.com.leandro.moviedb.util.parseObject
import br.com.leandro.moviedb.util.scheduler.ImmediateSchedulerProvider
import br.com.leandro.moviedbservice.model.MovieDetail
import br.com.leandro.moviedbservice.movie.MovieRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.mockito.Mockito.*

/**
 * Created by leandro on 18/06/2018
 */

class MovieViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application
    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var movieViewModel: MovieViewModel

    private lateinit var isLoadingLiveMock: Observer<Boolean>
    private lateinit var hasErrorLiveMock: Observer<Boolean>
    private lateinit var movieDetailLiveMock: Observer<MovieDetail>

    private lateinit var movieDetailMock: MovieDetail

    @Before
    fun setupCheckoutViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        setupContext()

        setupViewModelMocks()
        setupMovieDetailMock()

        // Get a reference to the class under test
        movieViewModel = MovieViewModel(context, movieRepository, ImmediateSchedulerProvider())
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Mockito.`when`(context.resources).thenReturn(Mockito.mock(Resources::class.java))

    }

    private fun setupViewModelMocks() {
        isLoadingLiveMock = Mockito.mock(Observer::class.java) as Observer<Boolean>
        hasErrorLiveMock = Mockito.mock(Observer::class.java) as Observer<Boolean>
        movieDetailLiveMock = Mockito.mock(Observer::class.java) as Observer<MovieDetail>
    }

    private fun setupMovieDetailMock() {
        movieDetailMock = parseObject("movieDetailResponse.json", MovieDetail::class.java)
    }

    @Test
    fun loadMovieDetailWithSucess() {
        with(movieViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            movieLive.observeForever(movieDetailLiveMock)

            `when`(movieRepository.getMovieDetail(0)).thenReturn(io.reactivex.Single.just(movieDetailMock))

            loadMovieDetail(0)

            verify(isLoadingLiveMock).onChanged(true)
            verify(movieDetailLiveMock).onChanged(movieDetailMock)
            verify(isLoadingLiveMock).onChanged(false)

            verify(hasErrorLiveMock, never()).onChanged(true)
        }
    }

    @Test
    fun loadMovieDetailWithError() {
        with(movieViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            movieLive.observeForever(movieDetailLiveMock)

            `when`(movieRepository.getMovieDetail(0)).thenReturn(Single.error(RuntimeException("")))

            loadMovieDetail(0)

            verify(hasErrorLiveMock).onChanged(true)
            verify(hasErrorLiveMock, never()).onChanged(false)
        }
    }
}