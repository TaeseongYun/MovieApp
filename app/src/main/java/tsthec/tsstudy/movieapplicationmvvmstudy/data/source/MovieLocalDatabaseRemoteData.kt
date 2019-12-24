package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieDetailResponse
import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase

class MovieLocalDatabaseRemoteData(movieDatabase: MovieDatabase) {

    private val movieDAO = movieDatabase.movieResultDAO()

    fun inputMovieResult(movieResult: MovieResult)=
        movieDAO.insertMovieResult(movieResult)

    fun loadMovieDatabase(paraID: Int) =
        movieDAO.getListFavoriteMovie(paraID)

}