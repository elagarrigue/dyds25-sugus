package edu.dyds.movies.data.external

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import edu.dyds.movies.domain.entity.*

class ExternalRepository (val tmdbHttpClient: HttpClient){
    public suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
        tmdbHttpClient.get("/3/movie/$id").body()
    public suspend fun getTMDBPopularMovies():  RemoteResult =
        //try {
            tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
        //} catch (e: Exception) {
            //emptyList()
        //}
}