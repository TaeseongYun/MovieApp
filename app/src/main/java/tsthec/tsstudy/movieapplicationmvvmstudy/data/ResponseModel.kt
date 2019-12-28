package tsthec.tsstudy.movieapplicationmvvmstudy.data


data class ResponseModel(
    val page: Int,
    val results: List<*>,
    val total_pages: Int,
    val total_results: Int
)