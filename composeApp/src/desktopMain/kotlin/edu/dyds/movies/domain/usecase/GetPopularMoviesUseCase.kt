package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.RemoteResult
import edu.dyds.movies.domain.repository.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(): RemoteResult {
        return repository.getTMDBPopularMovies()
    }
}