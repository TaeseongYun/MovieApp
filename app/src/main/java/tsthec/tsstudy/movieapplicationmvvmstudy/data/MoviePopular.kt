package tsthec.tsstudy.movieapplicationmvvmstudy.data


data class MoviePopular(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)