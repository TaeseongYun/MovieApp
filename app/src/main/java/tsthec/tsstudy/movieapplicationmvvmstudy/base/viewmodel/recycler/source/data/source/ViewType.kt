package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.recycler.source.data.source

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

sealed class AdapterViewType {
    enum class DataType {
        MOVIE,
        TV,
        GENRE,
        SEARCH
    }

    @Parcelize
    data class ViewType(val type: DataType, val item: @RawValue Any?) : AdapterViewType(), Parcelable
}