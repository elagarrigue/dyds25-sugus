package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class MoviesLocalSource {

    private val cacheMovies: MutableList<Movie> = mutableListOf()

    fun isEmpty() =
        cacheMovies.isEmpty()

    fun addAll(movies : List<Movie>) {
        cacheMovies.clear()
        cacheMovies.addAll(movies)
    }

    fun getAll() =
        cacheMovies
}