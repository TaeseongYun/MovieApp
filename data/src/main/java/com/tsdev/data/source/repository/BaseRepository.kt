package com.tsdev.data.source.repository

import java.util.*

abstract class BaseRepository<ITEM> {
    val mutableMapItem by lazy { mutableMapOf<ITEM?, Boolean>() }

    val currentLanguage = Locale.getDefault().language
}