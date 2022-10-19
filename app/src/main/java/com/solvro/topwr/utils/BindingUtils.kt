package com.solvro.topwr.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

object BindingUtils {

    @BindingAdapter("visible")
    @JvmStatic
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Glide
                .with(view)
                .load(it)
                .centerCrop()
                .into(view)
        }
    }

    @BindingAdapter("setAdapter")
    @JvmStatic
    fun setAdapter(
        recyclerView: RecyclerView,
        adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?
    ) {
        adapter?.let {
            recyclerView.adapter = it
        }
    }

    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("submitList")
    @JvmStatic
    fun submitList(recyclerView: RecyclerView, list: List<ListAdapterItem>?) {
        val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
        adapter?.updateData(list ?: listOf())
    }

}