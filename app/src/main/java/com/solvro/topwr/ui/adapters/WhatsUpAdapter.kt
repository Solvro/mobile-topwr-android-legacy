package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.databinding.WhatsUpItemBinding

class WhatsUpAdapter(
    private val notices: List<Notices>,
    private val onClick: (Notices, ImageView, TextView, TextView, TextView) -> Unit
) : RecyclerView.Adapter<WhatsUpAdapter.ViewHolder>() {
    inner class ViewHolder(binding: WhatsUpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.moreButton.setOnClickListener {
                onClick(notices[adapterPosition], whatsupItemImage, whatsUpTitle, whatsUpDate, whatsUpDescription)
            }
        }

        private val whatsupItemImage = binding.whatsUpImageView
        private val whatsUpDate = binding.dateTextView
        private val whatsUpTitle = binding.titleTextView
        private val whatsUpDescription = binding.descriptionTextView
        fun bind() {
            val options: RequestOptions = RequestOptions().centerCrop().transform(
                CenterCrop(),
                GranularRoundedCorners (8F,
                8F,
                0F,
                0F
            )
            )
            Glide.with(whatsupItemImage).load(notices[adapterPosition].photo?.url)
                .apply(options)
                .into(whatsupItemImage)
            val dateArray: List<String>? = notices[adapterPosition].created_at?.split("-")
            whatsUpDate.text = dateArray?.get(2)?.substring(0,2) + "." + (dateArray?.get(1)) + "." + (dateArray?.get(0))
            whatsUpTitle.text = notices[adapterPosition].title
            whatsUpDescription.text = notices[adapterPosition].description

            whatsupItemImage.transitionName = notices[adapterPosition].photo!!.id.toString()
            whatsUpTitle.transitionName = notices[adapterPosition].title.toString()
            whatsUpDate.transitionName = notices[adapterPosition].created_at+notices[adapterPosition].id
            whatsUpDescription.transitionName = notices[adapterPosition].description.toString()
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