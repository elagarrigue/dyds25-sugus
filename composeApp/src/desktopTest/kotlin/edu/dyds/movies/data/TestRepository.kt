package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


private val MOVIE_LIST =
    listOf(
        FakeMovieFactory.create(1, "Titulo", 1.0),
        FakeMovieFactory.create(2, "Titulo2", 2.0),
        FakeMovieFactory.create(3, "Titulo3", 3.0)
    )

class TestRepository {

    private var localSource: MoviesLocalSource = mockk(relaxed = true)
    private var externalSource: MoviesExternalSource = mockk(relaxed = true)

    @Test
    fun `Test getPopularMovies desde external source`() {
        //Arrange
        every { localSource.isEmpty() } returns true
        coEvery { externalSource.getTMDBPopularMovies() } returns MOVIE_LIST
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val expectedResult = MOVIE_LIST

        //Act
        var result: List<Movie> = emptyList()
        runTest { result = repository.getPopularMovies() }

        //Assert
        assertEquals(expectedResult, result)
        verify { localSource.addAll(any()) }
    }

    @Test
    fun `Test getPopularMovies desde local source`() {
        //Arrange
        every { localSource.isEmpty() } returns false
        every { localSource.getAll() } returns MOVIE_LIST
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val expectedResult = MOVIE_LIST

        //Act
        var result: List<Movie> = emptyList()
        runTest { result = repository.getPopularMovies() }

        //Assert
        assertEquals(expectedResult, result)
        coVerify(exactly = 0) { externalSource.getTMDBPopularMovies() }
    }

    @Test
    fun `Test getPopularMovies falla en external source`() {
        //Arrange
        coEvery { externalSource.getTMDBPopularMovies() } throws Exception()
        every { localSource.isEmpty() } returns true
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val expectedResult = emptyList<Movie>()

        //Act
        var result: List<Movie> = emptyList()
        runTest { result = repository.getPopularMovies() }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Test getMovieDetails`() {
        //Arrange
        coEvery { externalSource.getTMDBMovieDetails(any()) } returns FakeMovieFactory.create(3)
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val movieId = 3
        val expectedResult = FakeMovieFactory.create(3)

        //Act
        var result: Movie? = null
        runTest { result = repository.getMovieDetails(movieId) }

        //Assert
        assertEquals(expectedResult, result)
    }

    @Test
    fun `Test getMovieDetails falla`() {
        //Arrange
        coEvery { externalSource.getTMDBMovieDetails(any()) } throws Exception()
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val movieId = 3
        val expectedResult = null

        //Act
        var result: Movie? = null
        runTest { result = repository.getMovieDetails(movieId) }

        //Assert
        assertEquals(expectedResult, result)
    }
}