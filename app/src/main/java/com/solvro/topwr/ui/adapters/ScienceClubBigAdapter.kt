package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.databinding.ScienceClubsItemBinding
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub

class ScienceClubBigAdapter(
    diffCallback: DiffUtil.ItemCallback<ScienceClub>,
    private val onItemClick: (ScienceClub) -> Unit
) :
    PagingDataAdapter<ScienceClub, ScienceClubBigAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ScienceClubsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScienceClub?) {
            binding.apply {
                root.setOnClickListener {
                    item?.let {
                        onItemClick.invoke(
                            it
                        )
                    }
                }
                scienceClubNameTextView.text = item?.name
                scienceClubDescriptionTextView.text = item?.description
                Glide
                    .with(root.context)
                    .load(item?.photo?.url)
                    .apply(RequestOptions().override(100, 100)) //change to 100x100px img
                    .centerCrop()
                    .into(scienceClubsImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ScienceClubsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}