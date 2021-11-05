package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.WhatsUpItemBinding

class WhatsUpAdapter (private val departments: List<DepartmentItem>,
private val onClick: (DepartmentItem) -> Unit): RecyclerView.Adapter<WhatsUpAdapter.ViewHolder>() {
    inner class ViewHolder(binding: WhatsUpItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(departments[adapterPosition])
            }
        }
        private val whatsupItemImage = binding.whatsUpImageView
        private val whatsUpDate = binding.dateTextView
        private val whatsUpTitle = binding.titleTextView
        private val whatsUpDescription = binding.descriptionTextView
        fun bind(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsUpAdapter.ViewHolder {
        val binding = WhatsUpItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WhatsUpAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = departments.size

}