package com.solvro.topwr.ui.fragments.home_page

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.databinding.DepartmentsHomeItemBinding
import com.solvro.topwr.utils.Constants.PLACEHOLDER_COLOR


class DepartmentsHomeAdapter(
    private val onClick: (Departments) -> Unit
) : RecyclerView.Adapter<DepartmentsHomeAdapter.DepartmentsViewHolder>() {

    private val departments = mutableListOf<Departments>()

    inner class DepartmentsViewHolder(private val binding: DepartmentsHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(department: Departments) {
            val gradientDrawable = generateGradientDrawable(
                department.color?.gradientFirst ?: PLACEHOLDER_COLOR,
                department.color?.gradientSecond ?: PLACEHOLDER_COLOR
            )
            binding.apply {
                Glide.with(departmentsItemImage).load(gradientDrawable)
                    .into(departmentsItemImage)
                Glide.with(departmentsLogoItemImage).load(department.logo?.url)
                    .into(departmentsLogoItemImage)

                departmentsItemNameTextView.text = department.name
                departmentsItemIdTextView.text = department.code
                root.setOnClickListener {
                    onClick(department)
                }
            }
        }

        private fun generateGradientDrawable(firstHex: String, secondHex: String) =
            GradientDrawable(
                GradientDrawable.Orientation.BR_TL,
                intArrayOf(
                    Color.parseColor(firstHex),
                    Color.parseColor(secondHex)
                )
            ).apply { cornerRadius = 0f }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding = DepartmentsHomeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) =
        holder.bind(departments[position])

    override fun getItemCount(): Int = departments.size

    fun setData(departments: List<Departments>) {
        this.departments.apply {
            clear()
            addAll(departments)
        }
        notifyDataSetChanged()
    }
}