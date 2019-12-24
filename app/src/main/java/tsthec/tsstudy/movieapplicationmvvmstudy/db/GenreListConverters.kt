package tsthec.tsstudy.movieapplicationmvvmstudy.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tsthec.tsstudy.movieapplicationmvvmstudy.data.Genre

open class GenreListConverters {
    @TypeConverter
    fun fromString(value: String): List<Genre>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Genre>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Genre>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}