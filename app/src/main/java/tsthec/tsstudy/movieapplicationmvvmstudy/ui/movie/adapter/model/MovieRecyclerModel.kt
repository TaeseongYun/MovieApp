package tsthec.tsstudy.movieapplicationmvvmstudy.ui.movie.adapter.model

interface MovieRecyclerModel<T>  {
    fun addItems(item: T)

    fun getItem(position: Int): T

    fun notifiedChangedItem()

    fun getItemCount(): Int

    fun clearItems()
}