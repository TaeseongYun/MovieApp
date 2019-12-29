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

// 다른 티스토리 타 블로그에서 코드 참고 하였음. 돌아가는 과정 상세하게 공부하기
fun <T : ViewModel> Class<T>.inject(fragment: Fragment, customKey: String = "", onCreate: () -> T) =
    ViewModelProviders.of(fragment, onCreateViewModel(onCreate)).run {
        if (customKey.isNotEmpty()) {
            this.get(customKey, this@inject)
        } else
            this.get(this@inject)
    }

fun <T : ViewModel> Class<T>.inject(
    fragment: FragmentActivity,
    customKey: String = "",
    onCreate: () -> T
) =
    ViewModelProviders.of(fragment, onCreateViewModel(onCreate)).run {
        if (customKey.isNotEmpty()) {
            this.get(customKey, this@inject)
        } else
            this.get(this@inject)
    }

fun <T : ViewModel> onCreateViewModel(cls: () -> T) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return cls() as T
        }
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