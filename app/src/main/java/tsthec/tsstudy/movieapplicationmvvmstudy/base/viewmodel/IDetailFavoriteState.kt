package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel

interface IDetailFavoriteState<in T: Any?> {
    fun loadFirstLikeState(item: T)
}