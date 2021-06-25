package com.tsdev.data.di

import androidx.paging.PageKeyedDataSource
import com.tsdev.data.paging.datasource.MoviePagingDataSource
import com.tsdev.data.source.MovieResult
import org.koin.dsl.module

val pagingModule = module {
    single<PageKeyedDataSource<Int, MovieResult>> { MoviePagingDataSource(get()) }
}