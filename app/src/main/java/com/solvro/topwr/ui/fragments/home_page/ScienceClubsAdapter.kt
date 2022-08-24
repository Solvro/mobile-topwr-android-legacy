package com.solvro.topwr.ui.fragments.home_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.databinding.ScienceClubsItemBinding

class ScienceClubsAdapter(
    private val onClick: (ScienceClub) -> Unit
) : RecyclerView.Adapter<ScienceClubsAdapter.ViewHolder>() {

    private val scienceClubs = mutableListOf<ScienceClub>()

    inner class ViewHolder(
        private val binding: ScienceClubsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scienceClub: ScienceClub) {
            binding.apply {
                Glide.with(scienceClubsImageView)
                    .load(scienceClub.photo?.url)
                    .into(scienceClubsImageView)

                scienceClubsTextView.text = scienceClub.name
                root.setOnClickListener {
                    onClick(scienceClub)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScienceClubsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scienceClubs[position])
    }

    override fun getItemCount(): Int = scienceClubs.size

    fun setData(scienceClubs: List<ScienceClub>) {
        this.scienceClubs.apply {
            clear()
            addAll(scienceClubs)
        }
        notifyDataSetChanged()
    }
}