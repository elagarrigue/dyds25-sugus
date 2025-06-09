package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.*
import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesLocalSource: MoviesLocalSource,
    private val moviesExternalSource: MoviesExternalSource
    ) : MoviesRepository{

    override suspend fun getMovieDetails(id: Int): Movie? =
        moviesExternalSource.getTMDBMovieDetails(id)

    override suspend fun getPopularMovies(): List<Movie> {
        if (!moviesLocalSource.isEmpty()) {
            return moviesLocalSource.getAll()
        } else {
            val popularMovies: List<Movie> = moviesExternalSource.getTMDBPopularMovies()
            moviesLocalSource.addAll(popularMovies)
            return popularMovies
        }
    }
}