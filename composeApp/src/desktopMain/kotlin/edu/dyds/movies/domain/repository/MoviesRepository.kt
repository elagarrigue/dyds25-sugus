package edu.dyds.movies.domain.repository

import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.entity.RemoteResult
import io.ktor.client.*

interface MoviesRepository {
    val tmdbHttpClient : HttpClient
    suspend fun getTMDBMovieDetails(id: Int): RemoteMovie
    suspend fun getTMDBPopularMovies(): RemoteResult
}