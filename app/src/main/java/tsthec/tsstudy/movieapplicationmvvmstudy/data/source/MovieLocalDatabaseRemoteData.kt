package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase

class MovieLocalDatabaseRemoteData(movieDatabase: MovieDatabase) {

    private val movieDAO = movieDatabase.movieResultDAO()

    fun inputMovieResult(movieResult: MovieResult)=
        movieDAO.insertMovieResult(movieResult)

    fun loadMovieDatabase(paraID: Int) =
        movieDAO.getFavoriteMovie(paraID)

    fun loadMovieDatabaseList() =
        movieDAO.getListFavorite()

    fun deleteMovieDatabase(paraID: Int) =
        movieDAO.getFavoriteMovieDelete(paraID)
}