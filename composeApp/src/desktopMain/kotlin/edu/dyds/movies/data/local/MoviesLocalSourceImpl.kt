package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class MoviesLocalSourceImpl : MoviesLocalSource {

    private val cacheMovies: MutableList<Movie> = mutableListOf()

    override fun isEmpty() =
        cacheMovies.isEmpty()

    override fun addAll(movies : List<Movie>) {
        cacheMovies.clear()
        cacheMovies.addAll(movies)
    }

    override fun getAll() =
        cacheMovies
}