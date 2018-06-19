package br.com.leandro.moviedb.features.genre

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.res.Resources
import br.com.leandro.moviedb.genre.GenreViewModel
import br.com.leandro.moviedb.util.parseObject
import br.com.leandro.moviedb.util.scheduler.ImmediateSchedulerProvider
import br.com.leandro.moviedbservice.genre.GenreRepository
import br.com.leandro.moviedbservice.model.GenreResponse
import br.com.leandro.moviedbservice.model.MovieByGenreResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by leandro on 18/06/2018
 */
class GenreViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Application
    @Mock
    private lateinit var genreRepository: GenreRepository

    private lateinit var genreViewModel: GenreViewModel

    private lateinit var isLoadingLiveMock: Observer<Boolean>
    private lateinit var hasErrorLiveMock: Observer<Boolean>
    private lateinit var genreLiveMock: Observer<GenreResponse>
    private lateinit var movieByGenreLiveMock: Observer<MovieByGenreResponse>

    private lateinit var genresMock: GenreResponse
    private lateinit var movieByGenreMock: MovieByGenreResponse

    @Before
    fun setupCheckoutViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        setupContext()

        setupViewModelMocks()
        setupGenresMock()

        // Get a reference to the class under test
        genreViewModel = GenreViewModel(context, genreRepository, ImmediateSchedulerProvider())
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Mockito.`when`(context.resources).thenReturn(Mockito.mock(Resources::class.java))

    }

    private fun setupViewModelMocks() {
        isLoadingLiveMock = Mockito.mock(Observer::class.java) as Observer<Boolean>
        hasErrorLiveMock = Mockito.mock(Observer::class.java) as Observer<Boolean>
        movieByGenreLiveMock = Mockito.mock(Observer::class.java) as Observer<MovieByGenreResponse>
        genreLiveMock = Mockito.mock(Observer::class.java) as Observer<GenreResponse>
    }

    private fun setupGenresMock() {
        movieByGenreMock = parseObject("movieByGenreResponse.json", MovieByGenreResponse::class.java)
        genresMock = parseObject("genreResponse.json", GenreResponse::class.java)
    }

    @Test
    fun loadGenresWithSuccess() {
        with(genreViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            genresLive.observeForever(genreLiveMock)

            `when`(genreRepository.getGenres()).thenReturn(io.reactivex.Single.just(genresMock))

            loadGenres()

            verify(isLoadingLiveMock).onChanged(true)
            verify(genreLiveMock).onChanged(genresMock)
            verify(isLoadingLiveMock).onChanged(false)

            verify(hasErrorLiveMock, never()).onChanged(true)
        }
    }

    @Test
    fun loadGenresWithError() {
        with(genreViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            genresLive.observeForever(genreLiveMock)

            `when`(genreRepository.getGenres()).thenReturn(Single.error(RuntimeException("")))

            loadGenres()

            verify(isLoadingLiveMock).onChanged(false)
            verify(hasErrorLiveMock).onChanged(true)
            verify(hasErrorLiveMock, never()).onChanged(false)
        }
    }

    @Test
    fun loadMovieByGenreWithSuccess() {
        with(genreViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            moviesByGenreLive.observeForever(movieByGenreLiveMock)

            `when`(genreRepository.getMoviesByGenre(0)).thenReturn(io.reactivex.Single.just(movieByGenreMock))

            loadMoviesByGenre(0)

            verify(isLoadingLiveMock).onChanged(true)
            verify(movieByGenreLiveMock).onChanged(movieByGenreMock)
            verify(isLoadingLiveMock).onChanged(false)

            verify(hasErrorLiveMock, never()).onChanged(true)
        }
    }

    @Test
    fun loadMovieByGenreWithError() {
        with(genreViewModel) {
            hasErrorLive.observeForever(hasErrorLiveMock)
            isLoadingLive.observeForever(isLoadingLiveMock)
            moviesByGenreLive.observeForever(movieByGenreLiveMock)

            `when`(genreRepository.getMoviesByGenre(0)).thenReturn(Single.error(RuntimeException("")))

            loadMoviesByGenre(0)

            verify(isLoadingLiveMock).onChanged(false)
            verify(hasErrorLiveMock).onChanged(true)
            verify(hasErrorLiveMock, never()).onChanged(false)
        }
    }
}