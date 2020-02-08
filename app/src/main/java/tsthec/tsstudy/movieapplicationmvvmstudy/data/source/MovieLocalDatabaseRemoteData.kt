package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.data.MovieResult
import tsthec.tsstudy.movieapplicationmvvmstudy.data.TVResult
import tsthec.tsstudy.movieapplicationmvvmstudy.db.MovieDatabase

class MovieLocalDatabaseRemoteData(movieDatabase: MovieDatabase) {

    private val movieDAO = movieDatabase.movieResultDAO()

    private val tvDAO = movieDatabase.tvResultDAO()

    fun inputMovieResult(movieResult: MovieResult)=
        movieDAO.insertMovieResult(movieResult)

    fun loadMovieDatabase(paraID: Int?) =
        movieDAO.getFavoriteMovie(paraID)

    fun loadTVDatabase(paraID: Int?) =
        tvDAO.getFavoriteTV(paraID)

    fun loadMovieDatabaseList() =
        movieDAO.getListFavorite()

    fun deleteMovieDatabase(paraID: Int) =
        movieDAO.getFavoriteMovieDelete(paraID)

    fun inputTvResult(tvResult: TVResult?) = tvDAO.insertTv(tvResult)

    fun deleteTvDatabase(paraID: Int?) = tvDAO.getFavoriteTvDelete(paraID)

    fun getLoadTvList() = tvDAO.getTvListFavorite()
}