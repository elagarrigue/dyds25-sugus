import edu.dyds.movies.domain.entity.Movie

object FakeMovieFactory {
    fun create(id: Int = 1, title: String = "Fake Title", qualification: Double = 0.0): Movie {
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
            voteAverage = qualification
        )
    }
}