package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.*
import edu.dyds.movies.data.external.ExternalRepository
import edu.dyds.movies.data.local.MoviesCache
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val cacheMovies: MoviesCache,
    private val externalRepository: ExternalRepository
    ) : MoviesRepository{

    override suspend fun getMovieDetails(id: Int): Movie? =
        try {
            externalRepository.getTMDBMovieDetails(id).toDomainMovie()
        }catch(e: Exception){
            null
        }
    override suspend fun getPopularMovies(): List<Movie> {
        if (!cacheMovies.isEmpty()) {
            return cacheMovies.getAll()
        } else {
            val popularMovies: List<Movie> = getFormattedMovies()
            cacheMovies.addAll(popularMovies)
            return popularMovies
        }
    }
    private suspend fun getMoviesFromExternalRepo()=
        externalRepository.getTMDBPopularMovies()

    private suspend fun getFormattedMovies()=
        getMoviesFromExternalRepo().map {
            it.toDomainMovie()
        }
}