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
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform


class DepartmentsHomeAdapter(
    private val departments: List<Departments>,
    private val onClick: (Departments) -> Unit
) : RecyclerView.Adapter<DepartmentsHomeAdapter.DepartmentsViewHolder>() {
    inner class DepartmentsViewHolder(binding: DepartmentsHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(departments[adapterPosition])
            }
        }

        private val departmentsItemImage = binding.departmentsItemImage
        private val departmentsIdItemTextView = binding.departmentsItemIdTextView
        private val departmentsNameTextView = binding.departmentsItemNameTextView
        private val departmentsLogo = binding.departmentsLogoItemImage
        fun bind() {
            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.BR_TL,
                intArrayOf(
                    Color.parseColor(departments[adapterPosition].color?.gradientFirst),
                    Color.parseColor(departments[adapterPosition].color?.gradientSecond)
                )
            );
            gradientDrawable.cornerRadius = 0f;
            Glide.with(departmentsItemImage).load(gradientDrawable)
                .into(departmentsItemImage)
            Glide.with(departmentsLogo).load(departments[adapterPosition].logo?.url)
                .into(departmentsLogo)

            departmentsNameTextView.text = departments[adapterPosition].name
            departmentsIdItemTextView.text = departments[adapterPosition].code

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding = DepartmentsHomeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = departments.size
}