package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.repository.MoviesRepository

private const val MIN_VOTE_AVERAGE = 6.0

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    private val cacheMovies: MutableList<RemoteMovie> = mutableListOf()

    suspend operator fun invoke(): List<QualifiedMovie> {
        return getPopularMovies().sortAndMap()
    }

    private suspend fun getPopularMovies() =
        if (cacheMovies.isNotEmpty()) {
            cacheMovies
        } else {
            try {
                repository.getTMDBPopularMovies().results.apply {
                    cacheMovies.clear()
                    cacheMovies.addAll(this)
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

    private fun List<RemoteMovie>.sortAndMap(): List<QualifiedMovie> {
        return this
            .sortedByDescending { it.voteAverage }
            .map {
                QualifiedMovie(
                    movie = it.toDomainMovie(),
                    isGoodMovie = it.voteAverage >= MIN_VOTE_AVERAGE
                )
            }
    }


}