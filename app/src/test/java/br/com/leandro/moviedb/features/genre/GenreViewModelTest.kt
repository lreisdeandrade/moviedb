package br.com.leandro.moviedb.features.genre

import br.com.leandro.moviedb.AppContext
import br.com.leandro.moviedb.genre.GenreViewModel
import br.com.leandro.moviedb.util.scheduler.ImmediateSchedulerProvider
import br.com.leandro.moviedbservice.genre.GenreDataSource
import br.com.leandro.moviedbservice.genre.GenreRepository
import br.com.leandro.moviedbservice.model.Genre
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

/**
 * Created by leandro on 18/06/2018
 */
class GenreViewModelTest {

    private lateinit var schedulerProvider: ImmediateSchedulerProvider
    private lateinit var genreViewModel: GenreViewModel
    private lateinit var genreDataSource: GenreDataSource
    private lateinit var genreRepository: GenreRepository

    private lateinit var genres: List<Genre>


    @Before
    fun setupGenreViewModel() {
        MockitoAnnotations.initMocks(this)

        // Make the sure that all schedulers are immediate.
        schedulerProvider = ImmediateSchedulerProvider()

        genreRepository = GenreRepository(genreDataSource)
        // Get a reference to the class under test

        genreViewModel = GenreViewModel(AppContext.instance, genreDataSource, schedulerProvider)

    }


    @Test
    fun loadGenres() {
        with(genreRepository) {
            // Given a stub active task with title and description added in the repository

//            loadGenres()
//            Task(TASK_TITLE, TASK_DESCRIPTION).also {
//                saveTask(it)
//
//                // When a task is completed using its id to the tasks repository
//                completeTask(it.id)
//                // Then the service API and persistent repository are called and the cache is updated
//                verify<TasksDataSource>(tasksRemoteDataSource).completeTask(it)
//                verify<TasksDataSource>(tasksLocalDataSource).completeTask(it)
//                assertThat(tasksRepository.cachedTasks.size, `is`(1))
//
//                val cachedNewTask = cachedTasks[it.id]
//                assertNotNull(cachedNewTask as Task)
//                assertThat(cachedNewTask.isActive, `is`(false))
//            }
        }
    }
}
