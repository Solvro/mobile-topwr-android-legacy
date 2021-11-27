package com.solvro.topwr.ui.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.databinding.DepartmentsHomeItemBinding


class DepartmentsHomeAdapter(private val departments: List<Departments>,
                             private val onClick: (Departments) -> Unit): RecyclerView.Adapter<DepartmentsHomeAdapter.DepartmentsViewHolder>() {
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
            departmentsNameTextView.text = departments[adapterPosition].Name
            departmentsIdItemTextView.text = departments[adapterPosition].Code
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BR_TL,
                intArrayOf(
                    Color.parseColor(departments[adapterPosition].Color?.GradientFirst),
                    Color.parseColor(departments[adapterPosition].Color?.GradientSecond))
            );
            gradientDrawable.cornerRadius = 0f;
            Glide.with(departmentsItemImage).load(gradientDrawable)
                .into(departmentsItemImage)
//            when (departments[adapterPosition].Code) {
//                "W1" -> Glide.with(departmentsItemImage).load(gradientDrawable)
//                    .into(departmentsItemImage)
//                "W2" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w2_bg)
//                    .into(departmentsItemImage)
//                "W3" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w3_bg)
//                    .into(departmentsItemImage)
//                "W4" -> Glide.with(departmentsItemImage).load(R.drawable.ic_w4_bg)
//                    .into(departmentsItemImage)
//            }
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