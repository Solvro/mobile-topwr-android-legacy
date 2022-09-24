package com.solvro.topwr.ui.fragments.home_page

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.solvro.topwr.R
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.databinding.WhatsUpItemBinding

class WhatsUpAdapter(
    private val onClick: (Notices, ImageView, TextView, TextView, TextView) -> Unit
) : RecyclerView.Adapter<WhatsUpAdapter.ViewHolder>() {

    private val notices = mutableListOf<Notices>()

    inner class ViewHolder(private val binding: WhatsUpItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: Notices) {
            binding.apply {
                Glide
                    .with(whatsUpImageView)
                    .load(notice.photo?.url)
                    .placeholder(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(whatsUpImageView)

                val dateArray: List<String>? = notice.created_at?.split("-")
                dateTextView.text = dateArray?.get(2)
                    ?.substring(0, 2) + "." + (dateArray?.get(1)) + "." + (dateArray?.get(0))
                titleTextView.text = notice.title
                descriptionTextView.text = notice.description

                val context = itemView.context
                setTransitionNames(context, notice)

                root.setOnClickListener {
                    onClick(
                        notice,
                        whatsUpImageView,
                        titleTextView,
                        dateTextView,
                        descriptionTextView
                    )
                }
            }
        }

        private fun setTransitionNames(context: Context, notice: Notices) {
            binding.apply {
                whatsUpImageView.transitionName =
                    context.getString(R.string.whatsup_image, notice.id)
                titleTextView.transitionName = context.getString(R.string.whatsup_title, notice.id)
                dateTextView.transitionName = context.getString(R.string.whatsup_date, notice.id)
                descriptionTextView.transitionName =
                    context.getString(R.string.whatsup_description, notice.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WhatsUpItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notices[position])
    }

    override fun getItemCount(): Int = notices.size

    fun setData(notices: List<Notices>) {
        this.notices.apply {
            clear()
            addAll(notices)
        }
        notifyDataSetChanged()
    }
}