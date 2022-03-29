package com.solvro.topwr.ui.fragments.map_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.ItemBuildingBinding

class BuildingsAdapter : RecyclerView.Adapter<BuildingsAdapter.ViewHolder>() {

    private val buildings: MutableList<Building> = mutableListOf()

    inner class ViewHolder(private val binding: ItemBuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(building: Building) {
            binding.apply {
                buildingCodeName.text = "Budynek " + building.code
                buildingAddress.text = building.addres
                Glide.with(binding.root.context)
                    .load(building.photo.url)
                    .into(buildingImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBuildingBinding = ItemBuildingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(buildings[position])
    }

    override fun getItemCount(): Int = buildings.size

    fun addData(items: List<Building>) {
        buildings.clear()
        buildings.addAll(items)
        notifyDataSetChanged()
    }
}