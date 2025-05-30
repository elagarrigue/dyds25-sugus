package edu.dyds.movies.domain.repository

import edu.dyds.movies.data.external.ExternalRepository
import edu.dyds.movies.data.local.MoviesCache
import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.*

interface MoviesRepository {
    suspend fun getTMDBMovieDetails(id: Int): Movie
    suspend fun getTMDBPopularMovies():  List<Movie>
}