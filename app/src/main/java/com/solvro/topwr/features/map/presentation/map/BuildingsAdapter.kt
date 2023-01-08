package com.solvro.topwr.features.map.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ItemBuildingBinding
import com.solvro.topwr.features.map.domain.model.Building

data class BuildingItemList(
    val building: Building,
    val isSelected: Boolean
)

class BuildingsAdapter(
    private val onItemClick: (Building) -> Unit
) : ListAdapter<BuildingItemList, BuildingsAdapter.ViewHolder>(BuildingsDiffCallback()) {

    private var buildings = listOf<BuildingItemList>()
    private var selectedBuilding: Building? = null
    private var searchHistory: List<Int> = listOf()
    var searchText: String = ""
        set(value) {
            field = value.lowercase()
            submitList(
                getFilteredItemsWithText(field)
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
                        if (isSelected) context.getColor(
                            R.color.white
                        ) else context.getColor(R.color.text_black)
                    )
                    text =
                        context.getString(R.string.building_name, building.code)
                }

                buildingAddress.apply {
                    setTextColor(
                        if (isSelected) context.getColor(
                            R.color.white
                        ) else context.getColor(R.color.text_black2)
                    )
                    text = building.address
                }

                Glide.with(binding.root.context)
                    .load(building.photoUrl)
                    .placeholder(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
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
        val sorted = sortItems(items.map {
            BuildingItemList(it, it == selectedBuilding)
        })
        buildings = sorted
        submitList(sorted)
    }

    fun setSelectedBuilding(building: Building?) {
        selectedBuilding = building
        val sorted =
            sortItems(currentList.map {
                BuildingItemList(
                    it.building,
                    it.building == selectedBuilding
                )
            })

        submitList(sorted)
    }

    fun setSearchHistory(searchHistory: List<Int>) {
        this.searchHistory = searchHistory
        val sorted =
            sortItems(currentList.map {
                BuildingItemList(
                    it.building,
                    it.building == selectedBuilding
                )
            })
        submitList(sorted)
    }

    private fun sortItems(items: List<BuildingItemList>): List<BuildingItemList> {
        val selectedItems = items.filter { it.isSelected }
        val searchHistoryItems = items.filter {
            searchHistory.contains(it.building.id) && !selectedItems.contains(it)
        }.sortedByDescending {
            searchHistory.indexOf(it.building.id)
        }
        val restItems =
            items.filter { !selectedItems.contains(it) && !searchHistoryItems.contains(it) }
                .sortedBy { it.building.code }

        return selectedItems.plus(searchHistoryItems)
            .plus(restItems)
    }

    private fun getFilteredItemsWithText(text: String): List<BuildingItemList> {
        return if (text.isNotBlank()) buildings.filter {
            it.building.code?.lowercase()?.contains(
                text
            ) ?: false || it.building.name?.lowercase()?.contains(
                text
            ) ?: false || it.building.address?.lowercase()?.contains(
                text
            ) ?: false
        }
        else buildings
    }
}

class BuildingsDiffCallback : DiffUtil.ItemCallback<BuildingItemList>() {
    override fun areItemsTheSame(oldItem: BuildingItemList, newItem: BuildingItemList): Boolean =
        oldItem.building.id == newItem.building.id


    override fun areContentsTheSame(oldItem: BuildingItemList, newItem: BuildingItemList): Boolean =
        oldItem.building == newItem.building && oldItem.isSelected == newItem.isSelected
}