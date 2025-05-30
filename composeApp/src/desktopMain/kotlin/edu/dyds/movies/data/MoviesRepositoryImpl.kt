package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.entity.RemoteResult
import edu.dyds.movies.domain.repository.MoviesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesRepositoryImpl(override val tmdbHttpClient: HttpClient) : MoviesRepository{

    private val cacheMovies: MutableList<RemoteMovie> = mutableListOf()

    override suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
        tmdbHttpClient.get("/3/movie/$id").body()

    override suspend fun getTMDBPopularMovies():  List<RemoteMovie> =
        if (cacheMovies.isNotEmpty()) {
            cacheMovies
        } else {
            try {
                getMoviesFromDB().results.apply {
                    cacheMovies.clear()
                    cacheMovies.addAll(this)
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

    private suspend fun getMoviesFromDB() : RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()
}