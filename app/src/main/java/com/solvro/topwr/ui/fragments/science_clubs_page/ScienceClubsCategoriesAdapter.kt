package com.solvro.topwr.ui.fragments.science_clubs_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ItemCategoryBinding
import kotlin.random.Random

class ScienceClubsCategoriesAdapter :
    RecyclerView.Adapter<ScienceClubsCategoriesAdapter.ViewHolder>() {

    // TODO: Remove fake categories
    private val data = ArrayList<String>(
        listOf(
            "Techologia",
            "Budownictwo",
            "Programowanie",
            "Druk 3D",
            "Motoryzacja"
        )
    )

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.scienceClubCategoryNameTextView.text = data[position]
            binding.isChecked = Random.nextBoolean()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = data.size

}