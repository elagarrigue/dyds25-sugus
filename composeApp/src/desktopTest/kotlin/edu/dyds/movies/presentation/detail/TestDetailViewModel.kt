package edu.dyds.movies.presentation.detail

import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class TestDetailViewModel {

    val testDispatcher = UnconfinedTestDispatcher()
    val testScope = CoroutineScope(testDispatcher)

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var getMovieDetailViewModel: GetMovieDetailsUseCase

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getMovieDetailViewModel = mockk()
        detailViewModel = DetailViewModel(getMovieDetailViewModel)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit loading and success`() = runTest {
        // Arrange
        val fakeMovie = FakeMovieFactory.create(11, "Fake 11")
        coEvery { getMovieDetailViewModel(11) } returns fakeMovie

        lateinit var moviesUiState: DetailViewModel.MovieDetailUiState
        testScope.launch {
            detailViewModel.movieDetailStateFlow.collect { moviesUiState = it }
        }

        // Act
        detailViewModel.getMovieDetail(11)
        advanceUntilIdle()

        // Assert
        assertFalse(moviesUiState.isLoading)
        assertEquals(fakeMovie, moviesUiState.movie)
    }

    @Test
    fun `should emit loading and null`() = runTest {
        // Arrange
        coEvery { getMovieDetailViewModel(11) } returns null

        lateinit var moviesUiState: DetailViewModel.MovieDetailUiState
        testScope.launch {
            detailViewModel.movieDetailStateFlow.collect { moviesUiState = it }
        }

        // Act
        detailViewModel.getMovieDetail(11)
        advanceUntilIdle()

        // Assert
        assertFalse(moviesUiState.isLoading)
        assertEquals(null, moviesUiState.movie)
    }
}