package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.SearchInterface

class MultiSearchRemoteDataSource(private val searchAPI: SearchInterface) {

    fun remoteDataMultiSource(query: String, page: Int) =
        searchAPI.multiSearch(query = query, page = page)
}