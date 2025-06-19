import edu.dyds.movies.data.local.*
import edu.dyds.movies.domain.entity.Movie
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class TestMoviesLocalSource {

    private lateinit var moviesLocalSource: MoviesLocalSource

    @BeforeTest
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