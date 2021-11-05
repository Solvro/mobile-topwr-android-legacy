package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.DepartmentsHomeItemBinding


class DepartmentsHomeAdapter(private val departments: List<DepartmentItem>,
                             private val onClick: (DepartmentItem) -> Unit): RecyclerView.Adapter<DepartmentsHomeAdapter.DepartmentsViewHolder>() {
    inner class DepartmentsViewHolder (binding: DepartmentsHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(departments[adapterPosition])
            }
        }

        private val departmentsItemImage = binding.departmentsItemImage
        private val departmentsIdItemTextView = binding.departmentsItemIdTextView
        private val departmentsNameTextView = binding.departmentsItemNameTextView
        fun bind() {
            departmentsNameTextView.text = departments[adapterPosition].name
            departmentsIdItemTextView.text = departments[adapterPosition].code
            when (departments[adapterPosition].code) {
                "W-1" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w1_bg)
                    .into(departmentsItemImage)
                "W-2" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w2_bg)
                    .into(departmentsItemImage)
                "W-3" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w3_bg)
                    .into(departmentsItemImage)
                "W-4" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w4_bg)
                    .into(departmentsItemImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding = DepartmentsHomeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = departments.size
}