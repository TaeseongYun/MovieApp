@file:Suppress("UNCHECKED_CAST")

package tsthec.tsstudy.movieapplicationmvvmstudy.util

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.loadFragment(@IdRes frameLayout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().run { this.replace(frameLayout, fragment).commit() }
}

inline fun scrollListener(
    crossinline handler: (totalItemCount: Int, visibleItem: Int, firstViewItemIndex: Int) -> Unit
) = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstViewItemIndex =
            (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        val visibleItem = recyclerView.childCount
        val totalItemCount = recyclerView.adapter?.itemCount ?: 0
        handler(totalItemCount, visibleItem, firstViewItemIndex)
    }
}