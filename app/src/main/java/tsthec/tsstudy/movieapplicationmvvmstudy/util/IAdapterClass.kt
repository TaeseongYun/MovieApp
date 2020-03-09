package tsthec.tsstudy.movieapplicationmvvmstudy.util

import android.view.View
import android.widget.AdapterView

interface IAdapterClass : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}