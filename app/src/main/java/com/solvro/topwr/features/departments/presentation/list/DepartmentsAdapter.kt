package com.solvro.topwr.features.departments.presentation.list

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.databinding.ItemDepartmentBinding

class DepartmentsAdapter(
    diffCallback: DiffUtil.ItemCallback<DepartmentsRemote>,
    private val onItemClick: (DepartmentsRemote) -> Unit
) :
    PagingDataAdapter<DepartmentsRemote, DepartmentsAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemDepartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemDepartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DepartmentsRemote) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                intArrayOf(
                    Color.parseColor(item.colorRemote?.gradientFirst!!),
                    Color.parseColor(item.colorRemote?.gradientSecond!!)
                )
            )
            with(binding) {
                Glide.with(departmentBackground)
                    .load(gradientDrawable)
                    .into(departmentBackground)
                Glide.with(departmentListItemLogo)
                    .load(item.logo?.url)
                    .override(120, 120)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(departmentListItemLogo)

                acronymTextView.text = item.code
                departmentNameTextView.text = item.name
                root.setOnClickListener {
                    onItemClick.invoke(item)
                }
            }
        }
    }
}

object DepartmentComparator : DiffUtil.ItemCallback<DepartmentsRemote>() {
    override fun areItemsTheSame(oldItem: DepartmentsRemote, newItem: DepartmentsRemote): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DepartmentsRemote, newItem: DepartmentsRemote): Boolean {
        return oldItem == newItem
    }
}
