package com.tsdev.data.local

import com.tsdev.data.db.dao.movie.MovieDAO
import com.tsdev.data.db.dao.tv.TvDAO
import com.tsdev.data.source.MovieResult
import com.tsdev.data.source.TVResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

internal class MovieLocalSourceDataImpl(private val movieDatabase: MovieDAO,
                                        private val tvDataBase: TvDAO) :
    MovieLocalSourceData {
    override fun deleteMovieDatabase(paraID: Int?): Completable {
        return movieDatabase.getFavoriteMovieDelete(paraID)
    }

    override fun loadMovieDatabaseList(): Single<List<MovieResult>> {
        return movieDatabase.getListFavorite()
    }

    override fun inputMovieResult(movieResult: MovieResult?): Completable {
        return movieDatabase.insertMovieResult(movieResult)
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