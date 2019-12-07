package tsthec.tsstudy.movieapplicationmvvmstudy.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

inline fun recyclerViewScrollListenr(
    crossinline callback: (visibleItemCount: Int, totalItemCount: Int, firstVisibleItem: Int) -> Unit
) = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleCount = recyclerView.childCount
        val firstViewIndex =
            (recyclerView.layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition() ?: 0
        val totalCountItem = recyclerView.adapter?.itemCount ?: 0

        callback(visibleCount, totalCountItem, firstViewIndex)
    }
}