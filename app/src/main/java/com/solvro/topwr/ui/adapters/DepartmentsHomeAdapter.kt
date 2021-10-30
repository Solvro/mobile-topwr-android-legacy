package com.solvro.topwr.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.DepartmentsHomeItemBinding
import com.solvro.topwr.databinding.RecentlySearchedItemBinding

class DepartmentsHomeAdapter(private val departments: List<DepartmentItem>,
                             private val onClick: (DepartmentItem) -> Unit): RecyclerView.Adapter<DepartmentsHomeAdapter.DepartmentsViewHolder>() {
    class DepartmentsViewHolder (binding: DepartmentsHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}