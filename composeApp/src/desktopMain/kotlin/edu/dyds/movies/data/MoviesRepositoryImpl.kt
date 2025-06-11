package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.*
import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesLocalSource: MoviesLocalSource,
    private val moviesExternalSource: MoviesExternalSource
) : MoviesRepository {

    override suspend fun getMovieDetails(id: Int): Movie? =
        try {
            moviesExternalSource.getTMDBMovieDetails(id)
        } catch (e: Exception) {
            null
        }

    override suspend fun getPopularMovies(): List<Movie> {
        return if (!moviesLocalSource.isEmpty()) {
            moviesLocalSource.getAll()
        } else {
            getMoviesFromRemote().also { movies ->
                moviesLocalSource.addAll(movies)
            }
        }
    }

    private suspend fun getMoviesFromRemote(): List<Movie> =
        try {
            moviesExternalSource.getTMDBPopularMovies()
        } catch (e: Exception) {
            emptyList()
        }
}