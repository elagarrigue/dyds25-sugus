package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface MoviesExternalSource {

    suspend fun getTMDBMovieDetails(id: Int): Movie

    suspend fun getTMDBPopularMovies(): List<Movie>
}

