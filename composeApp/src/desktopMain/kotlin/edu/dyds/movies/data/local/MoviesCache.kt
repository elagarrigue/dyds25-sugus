package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class MoviesCache {
    private val cacheMovies: MutableList<Movie> = mutableListOf()

    fun isEmpty() =
        cacheMovies.isEmpty()

    fun clear() =
        cacheMovies.clear()

    fun addAll(movies : List<Movie>) =
        cacheMovies.addAll(movies)

    fun getAll() =
        cacheMovies
}