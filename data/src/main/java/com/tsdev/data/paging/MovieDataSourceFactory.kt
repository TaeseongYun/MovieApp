package com.tsdev.data.paging

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.tsdev.data.source.MovieResult

internal class MovieDataSourceFactory constructor(
    private val dataPagingSource: PageKeyedDataSource<Int, MovieResult>
) : DataSource.Factory<Int, MovieResult>() {

    override fun create(): DataSource<Int, MovieResult> {
        return dataPagingSource
    }

    companion object {
        private const val PAGE_SIZE = 20
        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
    }
}