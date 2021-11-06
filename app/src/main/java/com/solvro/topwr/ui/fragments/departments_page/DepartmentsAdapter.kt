package com.solvro.topwr.ui.fragments.departments_page

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.ItemDepartmentBinding

class DepartmentsAdapter : RecyclerView.Adapter<DepartmentsAdapter.ViewHolder>() {

    //TODO: Fetch data from network
    private var data: ArrayList<DepartmentItem> = arrayListOf(
        DepartmentItem("id1", "Wydział Architektury", "W-1", "image1"),
        DepartmentItem("id1", "Wydział Budownictwa Wodnego i Lądowego", "W-2", "image2"),
        DepartmentItem("id1", "Wydział Chemiczny", "W-3", "image3"),
        DepartmentItem("id1", "Wydział Elektroniki", "W-4", "image4"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemDepartmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemDepartmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DepartmentItem) {
            binding.acronymTextView.text = item.code
            binding.departmentNameTextView.text = item.name
            binding.departmentLogo.setImageDrawable(getBackground(item.imageUrl))
        }

        private fun getBackground(url: String): Drawable {
            val imageMap = mapOf(
                "image1" to R.drawable.ic_w1_bg,
                "image2" to R.drawable.ic_w2_bg,
                "image3" to R.drawable.ic_w3_bg,
                "image4" to R.drawable.ic_w4_bg,
            )
            return ContextCompat.getDrawable(binding.root.context, imageMap[url]!!)!!
        }
    }
}