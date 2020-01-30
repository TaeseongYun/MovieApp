package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source

interface MovieRecyclerModel {
    fun addItems(item: Any?)

    fun getItem(position: Int): Any?

    fun notifiedChangedItem()

    fun getItemCount(): Int

    fun clearItems()

//    fun getItemViewType(position: Int): Int

    fun listReserve()
}