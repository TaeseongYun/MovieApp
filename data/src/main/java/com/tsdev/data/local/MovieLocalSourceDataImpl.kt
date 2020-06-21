package com.tsdev.data.local

import com.tsdev.data.db.dao.movie.MovieDAO
import com.tsdev.data.db.dao.tv.TvDAO
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import io.reactivex.Single

internal class MovieLocalSourceDataImpl(private val movieDatabase: MovieDAO,
                                        private val tvDataBase: TvDAO) :
    MovieLocalSourceData {
    override fun deleteMovieDatabase(paraID: Int?) {
        movieDatabase.getFavoriteMovieDelete(paraID)
    }

    override fun loadMovieDatabaseList(): Single<List<MovieResult>> {
        return movieDatabase.getListFavorite()
    }

    override fun inputMovieResult(movieResult: MovieResult?) {
        movieDatabase.insertMovieResult(movieResult)
    }

    override fun inputTvResult(tvResult: TVResult?) {
        tvDataBase.insertTv(tvResult)
    }

    override fun deleteTvDatabase(paraID: Int?) {
        tvDataBase.getFavoriteTvDelete(paraID)
    }

    override fun getLoadTvList() =
        tvDataBase.getTvListFavorite()
}