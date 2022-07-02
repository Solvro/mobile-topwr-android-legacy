package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.BuildingsItemBinding

class BuildingsAdapter(
    private val buildings: List<Building>,
    private val onClick: (Building) -> Unit
) : RecyclerView.Adapter<BuildingsAdapter.RecentlySearchedViewHolder>() {
    inner class RecentlySearchedViewHolder(binding: BuildingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(buildings[adapterPosition])
            }
        }

        private val buildingItemImage = binding.buildingItemImage
        private val buildingItemTextView = binding.buildingItemTextView
        fun bind() {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
            buildingItemTextView.text = buildings[adapterPosition].code
            Glide.with(buildingItemImage)
                .load(buildings[adapterPosition].photo?.url).apply(options)
                .into(buildingItemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlySearchedViewHolder {
        val binding = BuildingsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentlySearchedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlySearchedViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = buildings.size
}
