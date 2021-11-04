package com.solvro.topwr.ui.fragments.science_clubs_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.scienceclub.ScienceClub
import com.solvro.topwr.databinding.ItemScienceClubBinding

class ScienceClubsAdapter : RecyclerView.Adapter<ScienceClubsAdapter.ViewHolder>() {

    //TODO: Fetch data from network
    val data = ArrayList<ScienceClub>(
        listOf(ScienceClub("1", "KN Solvro", "", "", "", "", listOf("")),
            ScienceClub("2", "SKN Gospodarki Przestrzennej", "", "", "", "", listOf("")),
            ScienceClub("3", "KN Projektantów Chemicznych “Consilium”", "", "", "", "", listOf("")),
            ScienceClub("4", "KN SISK", "", "", "", "", listOf("")))

    )

    inner class ViewHolder(private val binding: ItemScienceClubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScienceClub) {
            binding.apply {
                scienceClubItemName.text = item.name
                Glide
                    .with(root.context)
                    .load("https://picsum.photos/200")
                    .apply(RequestOptions().override(100, 100)) //change to 100x100px img
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .into(scienceClubItemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScienceClubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}