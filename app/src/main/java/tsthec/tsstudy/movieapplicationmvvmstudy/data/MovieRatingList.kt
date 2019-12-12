package tsthec.tsstudy.movieapplicationmvvmstudy.data

data class MovieRatingList(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)