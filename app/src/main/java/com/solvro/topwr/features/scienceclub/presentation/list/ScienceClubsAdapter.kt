package com.solvro.topwr.features.scienceclub.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.databinding.ItemScienceClubBinding
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub

class ScienceClubsAdapter(
    diffCallback: DiffUtil.ItemCallback<ScienceClub>,
    private val onItemClick: (ScienceClub, ImageView, TextView) -> Unit
) :
    PagingDataAdapter<ScienceClub, ScienceClubsAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemScienceClubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScienceClub?) {
            binding.apply {
                root.setOnClickListener {
                    item?.let {
                        onItemClick.invoke(
                            it,
                            scienceClubItemImage,
                            scienceClubItemName
                        )
                    }
                }
                scienceClubItemName.text = item?.name
                Glide
                    .with(root.context)
                    .load(item?.photo?.url)
                    .apply(RequestOptions().override(100, 100)) //change to 100x100px img
                    .centerCrop()
                    .into(scienceClubItemImage)
            }
            item?.let { setSharedTransitionNames(it) }
        }

        private fun setSharedTransitionNames(scienceClub: ScienceClub) {
            binding.apply {
                scienceClubItemName.transitionName = "${scienceClub.id}_name"
                scienceClubItemImage.transitionName = "${scienceClub.id}_image"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemScienceClubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}

object ScienceClubComparator : DiffUtil.ItemCallback<ScienceClub>() {
    override fun areItemsTheSame(oldItem: ScienceClub, newItem: ScienceClub): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ScienceClub, newItem: ScienceClub): Boolean {
        return oldItem == newItem
    }
}