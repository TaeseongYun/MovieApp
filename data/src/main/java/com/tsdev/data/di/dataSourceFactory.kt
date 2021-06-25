package com.tsdev.data.di

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.tsdev.data.paging.MovieDataSourceFactory
import com.tsdev.data.source.MovieResult
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceFactory = module {
    single<DataSource.Factory<Int, MovieResult>>(named("pagingFactory")) { MovieDataSourceFactory(get()) }

    single<PagedList.Config>(named("pagingConfig")) { MovieDataSourceFactory.pagedListConfig() }
}