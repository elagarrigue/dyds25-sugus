package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesExternalSourceImpl(val tmdbHttpClient: HttpClient): MoviesExternalSource{

    override suspend fun getTMDBMovieDetails(id: Int): Movie {
            val remoteMovie: RemoteMovie = tmdbHttpClient.get("/3/movie/$id").body()
            return remoteMovie.toDomainMovie()
    }

    override suspend fun getTMDBPopularMovies(): List<Movie> {
        val remoteResults: RemoteResult = tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
        return remoteResults.results.map {
            it.toDomainMovie()
        }
    }
}