package tsthec.tsstudy.movieapplicationmvvmstudy.util

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun AppCompatActivity.loadFragment(@IdRes frameLayout: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().run { this.replace(frameLayout, fragment).commit() }
}

