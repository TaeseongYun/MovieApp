@file:Suppress("UNCHECKED_CAST")

package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.loadFragment(@IdRes frameLayout: Int, fragment: Fragment) {
    supportFragmentManager.commit {
        replace(frameLayout, fragment)
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

fun Context.toast(text: CharSequence, duration: Int) = Toast.makeText(this, text, duration).show()