import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetPopularMoviesUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class TestGetPopularMoviesUseCase {

    class FakeRepositoryListaVacia : MoviesRepository {
        var functionCalled = false

        override suspend fun getMovieDetails(id: Int): Movie? = null

        override suspend fun getPopularMovies(): List<Movie> {
            functionCalled = true
            return emptyList()
        }
    }

    class FakeRepositoryListaConMovies : MoviesRepository {
        var functionCalled = false

        override suspend fun getMovieDetails(id: Int): Movie? = null

        override suspend fun getPopularMovies(): List<Movie> {
            functionCalled = true
            val list = mutableListOf<Movie>()
            list.add(FakeMovieFactory.create(1, "Titulo", 1.0))
            list.add(FakeMovieFactory.create(2, "Titulo2", 2.0))
            list.add(FakeMovieFactory.create(9, "Titulo9", 9.0))
            return emptyList()
        }
    }

    fun `test GetPopularMoviesUseCase con lista con elementos`() {
        //Arrange
        val repository = FakeRepositoryListaConMovies()
        val useCase = GetPopularMoviesUseCaseImpl(repository)
        val expectedResult = getExpectedResultsForTest()

        //Act
        var result: List<QualifiedMovie> = emptyList()
        runTest {
            result = useCase.invoke()
        }

        //Assert
        assert(repository.functionCalled)
        assertEquals(result, expectedResult)
    }

    private fun getExpectedResultsForTest(): List<QualifiedMovie> {
        val list = mutableListOf<QualifiedMovie>()
        list.add(
            QualifiedMovie(
                FakeMovieFactory.create(1, "Titulo", 1.0),
                false
            )
        )
        list.add(
            QualifiedMovie(
                FakeMovieFactory.create(2, "Titulo2", 2.0),
                false
            )
        )
        list.add(
            QualifiedMovie(
                FakeMovieFactory.create(9, "Titulo9", 9.0),
                true
            )
        )
        return list
    }
}