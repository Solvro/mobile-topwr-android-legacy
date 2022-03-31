package com.solvro.topwr.ui.fragments.map_page

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.ItemBuildingBinding

class BuildingsAdapter(
    private val onItemClick: (Building) -> Unit
) : RecyclerView.Adapter<BuildingsAdapter.ViewHolder>() {

    private val buildings: MutableList<Building> = mutableListOf()
    private var selectedBuilding: Building? = null

    inner class ViewHolder(private val binding: ItemBuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(building: Building) {
            binding.apply {
                val context = root.context
                val isSelected = building == selectedBuilding
                root.setOnClickListener { onItemClick(building) }
                buildingLayout.background = if (isSelected)
                    ContextCompat.getDrawable(context, R.drawable.gradient_background)
                else ContextCompat.getDrawable(context, R.color.grey)
                buildingCodeName.apply {
                    setTextColor(
                        if (!isSelected) context.getColor(R.color.text_black) else context.getColor(
                            R.color.white
                        )
                    )
                    text =
                        context.getString(R.string.building_name, building.code)
                }
                buildingAddress.apply {
                    setTextColor(
                        if (!isSelected) context.getColor(R.color.text_black2) else context.getColor(
                            R.color.white
                        )
                    )
                    text = building.addres
                }
                Glide.with(binding.root.context)
                    .load(building.photo.url)
                    .centerCrop()
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

    fun setSelectedBuilding(building: Building) {
        selectedBuilding = building
        notifyDataSetChanged()
    }
}