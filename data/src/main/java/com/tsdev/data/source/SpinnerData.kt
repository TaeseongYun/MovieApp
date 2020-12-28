package com.tsdev.data.source

import android.widget.AdapterView

enum class PROGRAM {
    TV, MOVIE
}

data class SpinnerData(val parent: AdapterView<*>?, var program: PROGRAM = PROGRAM.MOVIE) {
    fun fetchSpinner(): SpinnerData {
        return when (parent?.selectedItemPosition) {
            0 -> copy(program = PROGRAM.MOVIE)
            1 -> copy(program = PROGRAM.TV)
            else -> throw IllegalAccessError()
        }
    }
}