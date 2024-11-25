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
import com.solvro.topwr.databinding.ItemDepartmentBinding
import com.solvro.topwr.features.departments.domain.model.Department

class DepartmentsAdapter(
    diffCallback: DiffUtil.ItemCallback<Department>,
    private val onItemClick: (Department) -> Unit
) :
    PagingDataAdapter<Department, DepartmentsAdapter.ViewHolder>(diffCallback) {

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
        fun bind(item: Department) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                intArrayOf(
                    Color.parseColor(item.color?.gradientFirstValue!!),
                    Color.parseColor(item.color?.gradientSecondValue!!)
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

object DepartmentComparator : DiffUtil.ItemCallback<Department>() {
    override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem == newItem
    }
}
