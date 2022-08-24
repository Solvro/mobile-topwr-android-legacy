package com.solvro.topwr.ui.fragments.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.BuildingsItemBinding

class BuildingsAdapter(
    private val onClick: (Building) -> Unit
) : RecyclerView.Adapter<BuildingsAdapter.RecentlySearchedViewHolder>() {

    private val buildings = mutableListOf<Building>()

    inner class RecentlySearchedViewHolder(private val binding: BuildingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(building: Building) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
            binding.apply {
                buildingItemTextView.text = building.code
                Glide.with(buildingItemImage)
                    .load(building.photo?.url).apply(options)
                    .into(buildingItemImage)
                root.setOnClickListener {
                    onClick(building)
                }
            }
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
        holder.bind(buildings[position])
    }

    override fun getItemCount(): Int = buildings.size

    fun setData(buildings: List<Building>) {
        this.buildings.apply {
            clear()
            addAll(buildings)
        }
        notifyDataSetChanged()
    }
}
