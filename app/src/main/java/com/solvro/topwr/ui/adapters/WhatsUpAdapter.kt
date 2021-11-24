package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.databinding.WhatsUpItemBinding

class WhatsUpAdapter(
    private val notices: List<Notices>,
    private val onClick: (Notices) -> Unit
) : RecyclerView.Adapter<WhatsUpAdapter.ViewHolder>() {
    inner class ViewHolder(binding: WhatsUpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.moreButton.setOnClickListener {
                onClick(notices[adapterPosition])
            }
        }

        private val whatsupItemImage = binding.whatsUpImageView
        private val whatsUpDate = binding.dateTextView
        private val whatsUpTitle = binding.titleTextView
        private val whatsUpDescription = binding.descriptionTextView
        fun bind() {
            Glide.with(whatsupItemImage).load(notices[adapterPosition].Photo?.previewUrl)
                .into(whatsupItemImage)
            whatsUpDate.text = notices[adapterPosition].created_at
            whatsUpTitle.text = notices[adapterPosition].Title
            whatsUpDescription.text = notices[adapterPosition].Description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhatsUpAdapter.ViewHolder {
        val binding = WhatsUpItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WhatsUpAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = notices.size

}