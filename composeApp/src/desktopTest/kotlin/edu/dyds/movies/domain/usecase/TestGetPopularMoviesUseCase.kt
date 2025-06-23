package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

private val MOVIE_LIST =
    listOf(
        FakeMovieFactory.create(1, "Titulo", 1.0),
        FakeMovieFactory.create(2, "Titulo2", 2.0),
        FakeMovieFactory.create(9, "Titulo9", 9.0)
    )

class TestGetPopularMoviesUseCase {

    @Test
    fun `test GetPopularMoviesUseCase con lista con elementos`() {
        //Arrange
        val repository = mockk<MoviesRepository>()
        coEvery { repository.getPopularMovies() } returns MOVIE_LIST
        val useCase = GetPopularMoviesUseCaseImpl(repository)
        val expectedResult = getExpectedResultsForTest()

        //Act
        var result: List<QualifiedMovie> = emptyList()
        runTest {
            result = useCase.invoke()
        }

        //Assert
        assertEquals(expectedResult, result)
    }

    class FakeRepositoryListaConMovies : MoviesRepository {

        override suspend fun getMovieDetails(id: Int): Movie? = null

        override suspend fun getPopularMovies(): List<Movie> = MOVIE_LIST

    }

    private fun getExpectedResultsForTest(): List<QualifiedMovie> {
        val list = mutableListOf<QualifiedMovie>()
        list.add(
            QualifiedMovie(
                MOVIE_LIST[2],
                true
            )
        )
        list.add(
            QualifiedMovie(
                MOVIE_LIST[1],
                false
            )
        )
        list.add(
            QualifiedMovie(
                MOVIE_LIST[0],
                false
            )
        )
        return list
    }
}