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
            get(customKey, this@inject)
        } else
            this.get(this@inject)
    }

fun <VM : ViewModel> onCreateViewModel(cls: () -> VM) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return cls() as T
        }
    }
