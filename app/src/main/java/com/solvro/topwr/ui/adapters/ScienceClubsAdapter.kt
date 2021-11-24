package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClubs.ScienceClubs
import com.solvro.topwr.databinding.ScienceClubsItemBinding
import com.solvro.topwr.databinding.WhatsUpItemBinding

class ScienceClubsAdapter(
    private val scienceClubs: List<ScienceClubs>,
    private val onClick: (ScienceClubs) -> Unit
) : RecyclerView.Adapter<ScienceClubsAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ScienceClubsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(scienceClubs[adapterPosition])
            }
        }

        private val scienceClubsItemImage = binding.scienceClubsImageView
        private val scienceClubsTextView = binding.scienceClubsTextView

        fun bind() {
            Glide.with(scienceClubsItemImage).load(scienceClubs[adapterPosition].Photo?.url)
                .into(scienceClubsItemImage)
            scienceClubsTextView.text = scienceClubs[adapterPosition].Name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScienceClubsAdapter.ViewHolder {
        val binding = ScienceClubsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScienceClubsAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = scienceClubs.size
}