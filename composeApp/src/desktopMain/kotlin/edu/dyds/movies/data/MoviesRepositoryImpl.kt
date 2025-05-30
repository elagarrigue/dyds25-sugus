package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.*
import edu.dyds.movies.data.external.ExternalRepository
import edu.dyds.movies.data.local.MoviesCache
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val cacheMovies: MoviesCache,
    private val externalRepository: ExternalRepository
    ) : MoviesRepository{

    override suspend fun getTMDBMovieDetails(id: Int): Movie =
        externalRepository.getTMDBMovieDetails(id).toDomainMovie()

    override suspend fun getTMDBPopularMovies(): List<Movie> {
        if (!cacheMovies.isEmpty()) {
            return cacheMovies.getAll()
        } else {
            val popularMovies: List<Movie> = externalRepository.getTMDBPopularMovies().results.map {
                it.toDomainMovie()
            }
            cacheMovies.addAll(popularMovies)
            return popularMovies
        }
    }
}