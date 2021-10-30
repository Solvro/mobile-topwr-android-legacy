package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.R
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.RecentlySearchedItemBinding

class RecentlySearchedAdapter(
    private val departments: List<DepartmentItem>,
    private val onClick: (DepartmentItem) -> Unit
) : RecyclerView.Adapter<RecentlySearchedAdapter.RecentlySearchedViewHolder>() {
    inner class RecentlySearchedViewHolder(binding: RecentlySearchedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(departments[adapterPosition])
            }
        }

        val recentlySearchedItemImage = binding.recentlySearchedItemImage
        val recentlySearchedItemTextView = binding.recentlySearchedItemTextView
        fun bind() {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
            recentlySearchedItemTextView.text = departments[adapterPosition].code
            when (departments[adapterPosition].code) {
                "W-1" -> Glide.with(recentlySearchedItemImage)
                    .load(R.drawable.recently_searched_item).apply(options)
                    .into(recentlySearchedItemImage)
                "W-2" -> Glide.with(recentlySearchedItemImage)
                    .load(R.drawable.recently_searched_item).apply(options)
                    .into(recentlySearchedItemImage)
                "W-3" -> Glide.with(recentlySearchedItemImage)
                    .load(R.drawable.recently_searched_item).apply(options)
                    .into(recentlySearchedItemImage)
                "W-4" -> Glide.with(recentlySearchedItemImage)
                    .load(R.drawable.recently_searched_item).apply(options)
                    .into(recentlySearchedItemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlySearchedViewHolder {
        val binding = RecentlySearchedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentlySearchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlySearchedViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = departments.size
}
