package com.solvro.topwr.ui.fragments.science_clubs_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ItemCategoryBinding

class ScienceClubsCategoriesAdapter(
    private val onItemClick: (String) -> Unit
) :
    RecyclerView.Adapter<ScienceClubsCategoriesAdapter.ViewHolder>() {

    private val categories = ArrayList<String>()
    private val selected = ArrayList<String>()

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            categories[position].let { categoryName ->
                binding.apply {
                    scienceClubCategoryNameTextView.text = categoryName
                    isChecked = selected.contains(categoryName)
                    root.setOnClickListener {
                        onItemClick(categoryName)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = categories.size

    fun setData(categories: List<String>, selectedCategories: List<String>) {
        this.categories.clear()
        this.categories.addAll(categories)
        this.selected.clear()
        this.selected.addAll(selectedCategories)
        notifyDataSetChanged()
    }

}