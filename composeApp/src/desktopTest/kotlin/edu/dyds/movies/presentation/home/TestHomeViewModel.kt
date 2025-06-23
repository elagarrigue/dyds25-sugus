package edu.dyds.movies.presentation.home

import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCase
import edu.dyds.movies.presentation.detail.DetailViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

private val MOVIE_LIST =
    listOf(
        QualifiedMovie(
            FakeMovieFactory.create(4, "Titulo4", 10.0),
            true
        ),
        QualifiedMovie(
            FakeMovieFactory.create(3, "Titulo3", 7.0),
            true
        ),
        QualifiedMovie(
            FakeMovieFactory.create(2, "Titulo2", 4.0),
            false
        ),
        QualifiedMovie(
            FakeMovieFactory.create(1, "Titulo", 1.0),
            false
        )
    )

@OptIn(ExperimentalCoroutinesApi::class)
class TestHomeViewModel {
    val testDispatcher = UnconfinedTestDispatcher()
    val testScope = CoroutineScope(testDispatcher)

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPopularMoviesUseCase = mockk()
        homeViewModel = HomeViewModel(getPopularMoviesUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Test getAllMovies()`() {
        //Arrange
        coEvery { getPopularMoviesUseCase() } returns MOVIE_LIST
        lateinit var moviesUiState: HomeViewModel.MoviesUiState
        testScope.launch {
            homeViewModel.moviesStateFlow.collect { moviesUiState = it }
        }

        //Act
        homeViewModel.getAllMovies()

        //Assert
        assertEquals(moviesUiState.movies, MOVIE_LIST)
        assertEquals(moviesUiState.isLoading, false)
    }
}