package tsthec.tsstudy.movieapplicationmvvmstudy.base.viewmodel.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class AndroidExtensionsViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer