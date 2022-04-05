package com.solvro.topwr.ui.fragments.map_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.ItemBuildingBinding

class BuildingsAdapter(
    private val onItemClick: (Building) -> Unit
) : ListAdapter<BuildingItemList, BuildingsAdapter.ViewHolder>(BuildingsDiffCallback()) {

    private var buildings = listOf<BuildingItemList>()
    private var selectedBuilding: Building? = null
    var searchText: String = ""
        set(value) {
            field = value.lowercase()
            submitList(
                if (field.isNotBlank()) buildings.filter {
                    it.building.code.lowercase().contains(
                        field
                    ) || it.building.name.lowercase().contains(
                        field
                    ) || it.building.addres.lowercase().contains(
                        field
                    )
                }.sortWithCodeAndIsSelected()
                else buildings
            )
        }

    inner class ViewHolder(private val binding: ItemBuildingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(buildingItem: BuildingItemList) {
            binding.apply {
                val context = root.context
                val building = buildingItem.building
                val isSelected = buildingItem.isSelected
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
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    fun addItems(items: List<Building>) {
        val sorted = items.map {
            BuildingItemList(it, it == selectedBuilding)
        }.sortWithCodeAndIsSelected()
        buildings = sorted
        submitList(sorted)
    }

    fun setSelectedBuilding(building: Building?) {
        selectedBuilding = building
        val itemsSorted =
            currentList.map { BuildingItemList(it.building, it.building == selectedBuilding) }
                .sortWithCodeAndIsSelected()
        submitList(itemsSorted)
    }
}

class BuildingsDiffCallback : DiffUtil.ItemCallback<BuildingItemList>() {
    override fun areItemsTheSame(oldItem: BuildingItemList, newItem: BuildingItemList): Boolean =
        oldItem.building.id == newItem.building.id


    override fun areContentsTheSame(oldItem: BuildingItemList, newItem: BuildingItemList): Boolean =
        oldItem.building == newItem.building && oldItem.isSelected == newItem.isSelected
}

data class BuildingItemList(
    val building: Building,
    val isSelected: Boolean
)

fun List<BuildingItemList>.sortWithCodeAndIsSelected(): List<BuildingItemList> {
    return this.sortedWith(
        compareBy<BuildingItemList> {
            !it.isSelected
        }.thenBy {
            it.building.code
        }
    )
}