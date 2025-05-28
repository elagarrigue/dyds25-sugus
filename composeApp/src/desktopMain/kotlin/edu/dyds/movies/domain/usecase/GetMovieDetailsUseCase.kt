package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.RemoteMovie
import edu.dyds.movies.domain.repository.MoviesRepository

class GetMovieDetailsUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): RemoteMovie {
        return repository.getTMDBMovieDetails(movieId)
    }
}