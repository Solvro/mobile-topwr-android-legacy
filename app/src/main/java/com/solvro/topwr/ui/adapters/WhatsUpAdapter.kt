package com.solvro.topwr.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.R
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.databinding.WhatsUpItemBinding

class WhatsUpAdapter(
    private val notices: List<Notices>,
    private val onClick: (Notices, ImageView, TextView, TextView, TextView) -> Unit
) : RecyclerView.Adapter<WhatsUpAdapter.ViewHolder>() {
    inner class ViewHolder(binding: WhatsUpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val whatsupItemImage = binding.whatsUpImageView
        private val whatsUpDate = binding.dateTextView
        private val whatsUpTitle = binding.titleTextView
        private val whatsUpDescription = binding.descriptionTextView
        private val moreButton = binding.moreButton

        fun bind(notice: Notices) {
            val options: RequestOptions = RequestOptions().centerCrop().transform(
                CenterCrop(),
                GranularRoundedCorners (8F,
                8F,
                0F,
                0F
            )
            )
            Glide.with(whatsupItemImage).load(notice.photo?.url)
                .apply(options)
                .into(whatsupItemImage)
            val dateArray: List<String>? = notice.created_at?.split("-")
            whatsUpDate.text = dateArray?.get(2)?.substring(0,2) + "." + (dateArray?.get(1)) + "." + (dateArray?.get(0))
            whatsUpTitle.text = notice.title
            whatsUpDescription.text = notice.description

            val context = itemView.context
            setTransitionNames(context, notice)

            moreButton.setOnClickListener {
                onClick(notice, whatsupItemImage, whatsUpTitle, whatsUpDate, whatsUpDescription)
            }
        }

        private fun setTransitionNames(context: Context, notice: Notices) {
            whatsupItemImage.transitionName = context.getString(R.string.whatsup_image, notice.id)
            whatsUpTitle.transitionName = context.getString(R.string.whatsup_title, notice.id)
            whatsUpDate.transitionName = context.getString(R.string.whatsup_date, notice.id)
            whatsUpDescription.transitionName = context.getString(R.string.whatsup_description, notice.id)
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
        holder.bind(notices[position])
    }

    override fun getItemCount(): Int = notices.size

}