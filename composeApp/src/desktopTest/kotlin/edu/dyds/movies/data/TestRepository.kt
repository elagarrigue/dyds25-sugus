package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
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

    @Test
    fun `Test getPopularMovies desde external source`() {
        //Arrange
        val localSource = FakeLocalSourceEmpty()
        val externalSource = FakeExternalSourceWorking()
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val expectedResult = MOVIE_LIST

        //Act
        var result: List<Movie> = emptyList()
        runTest { result = repository.getPopularMovies() }

        //Assert
        assertEquals(expectedResult, result)
        assertEquals(localSource.savesMovies, true)
    }

    @Test
    fun `Test getPopularMovies desde local source`() {
        //Arrange
        val localSource = FakeLocalSourceNotEmpty()
        val externalSource = FakeExternalSourceWorking()
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val expectedResult = MOVIE_LIST

        //Act
        var result: List<Movie> = emptyList()
        runTest { result = repository.getPopularMovies() }

        //Assert
        assertEquals(expectedResult, result)
        assertEquals(false, externalSource.getTMDBPopularMoviesCalled)
    }

    @Test
    fun `Test getPopularMovies falla en external source`() {
        //Arrange
        val localSource = FakeLocalSourceEmpty()
        val externalSource = FakeExternalSourceFailing()
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
        val localSource = FakeLocalSourceEmpty()
        val externalSource = FakeExternalSourceWorking()
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
        val localSource = FakeLocalSourceEmpty()
        val externalSource = FakeExternalSourceFailing()
        val repository = MoviesRepositoryImpl(localSource, externalSource)
        val movieId = 3
        val expectedResult = null

        //Act
        var result: Movie? = null
        runTest { result = repository.getMovieDetails(movieId) }

        //Assert
        assertEquals(expectedResult, result)
    }

    class FakeExternalSourceWorking : MoviesExternalSource {
        var getTMDBPopularMoviesCalled = false
        override suspend fun getTMDBMovieDetails(id: Int) = FakeMovieFactory.create(id)

        override suspend fun getTMDBPopularMovies(): List<Movie> {
            getTMDBPopularMoviesCalled = true
            return MOVIE_LIST
        }
    }

    class FakeExternalSourceFailing : MoviesExternalSource {
        override suspend fun getTMDBMovieDetails(id: Int): Movie {
            throw Exception("Error")
        }

        override suspend fun getTMDBPopularMovies(): List<Movie> {
            throw Exception("Error")
        }
    }

    class FakeLocalSourceEmpty : MoviesLocalSource {
        var savesMovies = false
        override fun isEmpty() = true

        override fun addAll(movies: List<Movie>) {
            savesMovies = true
        }

        override fun getAll(): List<Movie> = emptyList()
    }

    class FakeLocalSourceNotEmpty : MoviesLocalSource {
        override fun isEmpty() = false

        override fun addAll(movies: List<Movie>) {
        }

        override fun getAll(): List<Movie> = MOVIE_LIST
    }
}