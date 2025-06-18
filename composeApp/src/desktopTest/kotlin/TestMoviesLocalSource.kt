import edu.dyds.movies.data.local.*
import edu.dyds.movies.domain.entity.Movie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TestMoviesLocalSource {

    private lateinit var moviesLocalSource: MoviesLocalSource

    object FakeMovieFactory {
        fun create(id: Int = 1, title: String = "Fake Title"): Movie {
            return Movie(
                id = id,
                title = title,
                overview = "This is a fake overview.",
                releaseDate = "2025-01-01",
                poster = "https://example.com/poster.jpg",
                backdrop = "https://example.com/backdrop.jpg",
                originalTitle = "Fake Original Title",
                originalLanguage = "en",
                popularity = 7.5,
                voteAverage = 8.2
            )
        }
    }

    @BeforeEach
    fun init() {
        moviesLocalSource = MoviesLocalSourceImpl()
    }

    @Test
    fun `isEmpty lista vacia`() {
        assertEquals(moviesLocalSource.isEmpty(), true)
    }

    @Test
    fun `isEmpty lista no vacia`() {
        moviesLocalSource.addAll(listOf(
            FakeMovieFactory.create(1, "pelicula1"),
            FakeMovieFactory.create(2, "pelicula2")
        ))
        assertEquals(moviesLocalSource.isEmpty(), false)
    }

    @Test
    fun `addAll funcionamiento normal`() {
        val secondList = listOf(
            FakeMovieFactory.create(1, "pelicula1"),
            FakeMovieFactory.create(2, "pelicula2")
        )

        moviesLocalSource.addAll(secondList)

        assertEquals(secondList, moviesLocalSource.getAll())
    }

    @Test
    fun `addAll limpia la lista antes`() {
        val firstList = listOf(FakeMovieFactory.create(1, "Movie 1"))
        val secondList = listOf(
            FakeMovieFactory.create(2, "Movie 2"),
            FakeMovieFactory.create(3, "Movie 3")
        )

        moviesLocalSource.addAll(firstList)
        moviesLocalSource.addAll(secondList)

        assertEquals(secondList, moviesLocalSource.getAll())
    }
}