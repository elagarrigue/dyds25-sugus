import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCase
import edu.dyds.movies.domain.usecase.GetMovieDetailsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class TestGetMovieDetailsUseCase {

    private lateinit var repository: MoviesRepository
    private lateinit var getMovieDetailUseCase: GetMovieDetailsUseCase

    @BeforeTest
    fun init() {
        repository = mockk()
        getMovieDetailUseCase = GetMovieDetailsUseCaseImpl(repository)
    }

    @Test
    fun `should return movie when the id is valid`() = runTest {
        val fakeMovie = FakeMovieFactory.create(11, "Madagascar")
        coEvery { repository.getMovieDetails(11) } returns fakeMovie

        val result = getMovieDetailUseCase(11)

        assertEquals(result, fakeMovie)
        coVerify(exactly = 1) { repository.getMovieDetails(11) }
    }

    @Test
    fun `should return null when the id is not valid`() = runTest {
        coEvery { repository.getMovieDetails(99) } returns null

        val result = getMovieDetailUseCase(99)

        assertEquals(null, result)
        coVerify(exactly = 1) { repository.getMovieDetails(99) }
    }
}