package edu.dyds.movies.data.external

import edu.dyds.movies.data.RemoteMovie
import edu.dyds.movies.data.RemoteResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ExternalRepository (val tmdbHttpClient: HttpClient){
    public suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
        tmdbHttpClient.get("/3/movie/$id").body()
    public suspend fun getTMDBPopularMovies(): List<RemoteMovie> =
        try {
            getRemoteResult().results
        } catch (e: Exception) {
            emptyList()
        }
    private suspend fun getRemoteResult(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
}