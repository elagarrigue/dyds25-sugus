package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.repository.MoviesRepository

private const val MIN_VOTE_AVERAGE = 6.0

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    suspend operator fun invoke(): List<QualifiedMovie> =
        getPopularMovies().sortByQualification().mapToQualification()

    private suspend fun getPopularMovies() =
        repository.getPopularMovies()

    private fun List<Movie>.sortByQualification(): List<Movie> =
        this.sortedByDescending { it.voteAverage }

    private fun List<Movie>.mapToQualification(): List<QualifiedMovie> =
        this.map {
            QualifiedMovie(
                movie = it,
                isGoodMovie = it.voteAverage >= MIN_VOTE_AVERAGE
            )
        }
}