package com.solvro.topwr.ui.fragments.departments_page

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.databinding.ItemDepartmentBinding

class DepartmentsAdapter(
    diffCallback: DiffUtil.ItemCallback<Departments>,
    private val onItemClick: (Departments) -> Unit
) :
    PagingDataAdapter<Departments, DepartmentsAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemDepartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(
        holder: DepartmentsAdapter.ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemDepartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Departments) {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                intArrayOf(
                    Color.parseColor(item.color?.gradientFirst!!),
                    Color.parseColor(item.color?.gradientSecond!!)
                )
            )
            with(binding) {
                Glide.with(departmentBackground)
                    .load(gradientDrawable)
                    .into(departmentBackground)
                Glide.with(departmentListItemLogo)
                    .load(item.logo?.url)
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

object DepartmentComparator : DiffUtil.ItemCallback<Departments>() {
    override fun areItemsTheSame(oldItem: Departments, newItem: Departments): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Departments, newItem: Departments): Boolean {
        return oldItem == newItem
    }
}
