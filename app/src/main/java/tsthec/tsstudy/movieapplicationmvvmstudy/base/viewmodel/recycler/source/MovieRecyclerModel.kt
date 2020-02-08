package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source

import tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source.AdapterViewType

interface MovieRecyclerModel {
    fun addItems(dataType: AdapterViewType.DataType, item: List<Any?>?)

    fun getItem(position: Int): Any?

    fun addItem(dataType: AdapterViewType.DataType, item: Any?)

    var notifiedChangedItem: () -> Unit

    fun getItemCount(): Int

    fun clearItems()

    fun removeAt(position: Int)

    fun List<Any?>.checkList(viewType: AdapterViewType.ViewType): Boolean
}