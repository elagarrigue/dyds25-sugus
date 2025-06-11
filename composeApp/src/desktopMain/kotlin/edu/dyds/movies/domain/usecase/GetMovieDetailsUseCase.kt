package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Movie?
}
