package com.solvro.topwr.ui.fragments.faq_page

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.AboutUsItemBinding

class AboutUsAdapter(
    private val onClick: (ImageView) -> Unit
): RecyclerView.Adapter<AboutUsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: AboutUsItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

                private val title = binding.aboutUsTitleTextView
                private val content = binding.aboutUsContent
                private val image = binding.aboutUsImageView

                fun bind() {
                    binding.root.setOnClickListener {
                        onClick(image)
                    }
                    setTransitionNames(itemView.context)
                }

                private fun setTransitionNames(context: Context) {
                    image.transitionName = context.getString(R.string.faq_image)
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AboutUsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }
}