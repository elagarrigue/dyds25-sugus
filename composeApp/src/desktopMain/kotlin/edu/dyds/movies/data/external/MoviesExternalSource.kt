package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ExternalRepository (val tmdbHttpClient: HttpClient){
    suspend fun getTMDBMovieDetails(id: Int): Movie? {
        try {
            val remoteMovie: RemoteMovie = tmdbHttpClient.get("/3/movie/$id").body()
            return remoteMovie.toDomainMovie()
        }catch(e: Exception){
            return null
        }
    }

    suspend fun getTMDBPopularMovies(): List<Movie> =
        try {
            getRemoteResult()
        } catch (e: Exception) {
            emptyList()
        }

    private suspend fun getRemoteResult(): List<Movie> {
        val remoteResults: RemoteResult = tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
        return remoteResults.results.map {
            it.toDomainMovie()
        }
    }
}