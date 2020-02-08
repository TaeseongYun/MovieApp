package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source

sealed class AdapterViewType {
    enum class DataType{
        MOVIE,
        TV,
        GENRE
    }
    data class ViewType(val type: DataType, val item: Any?): AdapterViewType()
}