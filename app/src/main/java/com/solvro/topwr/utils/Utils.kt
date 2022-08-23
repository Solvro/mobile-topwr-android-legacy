package com.solvro.topwr.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.data.model.date.Date
import java.util.*

class SpaceItemDecoration(private val spaceWidth: Int = 0, private val spaceHeight: Int = 0) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = spaceWidth
        outRect.bottom = spaceHeight
    }
}

val Int.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * This extension function allows to add loading or error state in initial data fetch
 * in Paging Recycler View. It combines LoadStateAdapter and PagingAdapter.
 * @return ConcatAdapter composed of PagingAdapter and LoadStateAdapter
 * */

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}

fun getAcademicScheduleDay(context: Context, date: Date): String {
    return with(context) {
        when (date.day) {
            Calendar.SUNDAY -> {
                if (date.even)
                    getString(R.string.Even_f) + " " + getString(R.string.Sunday)
                else
                    getString(R.string.Odd_f) + " " + getString(R.string.Sunday)
            }
            Calendar.MONDAY -> {
                if (date.even)
                    getString(R.string.Even) + " " + getString(R.string.Monday)
                else
                    getString(R.string.Odd) + " " + getString(R.string.Monday)
            }
            Calendar.TUESDAY -> {
                if (date.even)
                    context.getString(R.string.Even) + " " + getString(R.string.Tuesday)
                else
                    getString(R.string.Odd) + " " + getString(R.string.Tuesday)
            }
            Calendar.WEDNESDAY -> {
                if (date.even)
                    getString(R.string.Even_f) + " " + getString(R.string.Wednesday)
                else
                    getString(R.string.Odd_f) + " " + getString(R.string.Wednesday)
            }
            Calendar.THURSDAY -> {
                if (date.even)
                    getString(R.string.Even) + " " + getString(R.string.Thursday)
                else
                    getString(R.string.Odd) + " " + getString(R.string.Thursday)
            }
            Calendar.FRIDAY -> {
                if (date.even)
                    getString(R.string.Even) + " " + getString(R.string.Friday)
                else
                    getString(R.string.Odd) + " " + getString(R.string.Friday)
            }
            Calendar.SATURDAY -> {
                if (date.even)
                    getString(R.string.Even_f) + " " + getString(R.string.Saturday)
                else
                    getString(R.string.Odd_f) + " " + getString(R.string.Saturday)
            }
            else -> {
                "Unknown date"
            }
        }
    }
}