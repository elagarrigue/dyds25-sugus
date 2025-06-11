package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

interface MoviesLocalSource {

    fun isEmpty(): Boolean

    fun addAll(movies: List<Movie>)

    fun getAll(): List<Movie>
}
