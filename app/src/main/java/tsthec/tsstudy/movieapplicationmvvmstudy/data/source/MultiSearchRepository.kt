package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

import tsthec.tsstudy.movieapplicationmvvmstudy.network.SearchInterface

class MultiSearchRepository private constructor(private val multiSearchAPI: SearchInterface) {

    /*
    Singleton what diff about object keyword?
    If you declare keyword 'object' than you can use immediately instance without instance declared

    오브젝트 키워드는 선언과 동시에 객체가 생성된다. 디컴파일 해보면 final static 클래스로 생성과 동시에 객체를 만들어냄. 생성자 사용불가
    companion object 도 마찬가지로 static 변수를 만들어냄. 따로 이름을 정해주지 않으면 Companion명의 static 클래스가 생성됌.
     */
    companion object {
        private var instance: MultiSearchRepository? = null

        fun getInstance(multiSearchAPI: SearchInterface) =
            instance ?: synchronized(this) {
                instance ?: MultiSearchRepository(multiSearchAPI).also { instance = it }
            }
    }

    private val multiSearchRemoteDataSource: MultiSearchRemoteDataSource by lazy {
        MultiSearchRemoteDataSource(multiSearchAPI)
    }

    fun multiSearchRepository(query: String, page: Int) =
        multiSearchRemoteDataSource.remoteDataMultiSource(query, page)
}