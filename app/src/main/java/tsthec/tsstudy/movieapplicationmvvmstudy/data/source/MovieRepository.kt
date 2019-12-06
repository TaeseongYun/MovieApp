package tsthec.tsstudy.movieapplicationmvvmstudy.data.source

class MovieRepository private constructor() {
    companion object {
        private var instance: MovieRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MovieRepository().also { instance = it }
            }
    }
}