@file:Suppress("UNCHECKED_CAST")

package tsthec.tsstudy.movieapplicationmvvmstudy.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> createViewModel(onCreateViewModel: () -> T) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return onCreateViewModel() as T
        }

    }

fun <T : ViewModel> Class<T>.inject(fragmentActivity: FragmentActivity, customKey: String = "", onCreateViewModel: () -> T): T =
    ViewModelProviders.of(fragmentActivity, createViewModel(onCreateViewModel)).run {
        if (customKey.isNotEmpty()) {
            this.get(customKey, this@inject)
        } else {
            get(this@inject)
        }
    }

fun <T: ViewModel> Class<T>.inject(fragment: Fragment, customKey: String = "", onCreateViewModel: () -> T): T =
    ViewModelProviders.of(fragment, createViewModel(onCreateViewModel)).run {
        if(customKey.isNotEmpty())
            this.get(customKey, this@inject)
        else
            get(this@inject)
    }
