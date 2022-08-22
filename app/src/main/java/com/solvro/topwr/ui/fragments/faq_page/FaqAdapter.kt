package com.solvro.topwr.ui.fragments.faq_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.FaqItemBinding

class FaqAdapter(
    private val onClick: (Info) -> Unit
) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    private val infos = mutableListOf<Info>()

    inner class ViewHolder(private val binding: FaqItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val faqItemImage = binding.faqImageView
        private val faqTitle = binding.faqTitleTextView
        private val faqDescription = binding.textViewDescription
        fun bind(info: Info) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .transform(
                    CenterCrop()
                )

            Glide.with(faqItemImage)
                .load(info.photo?.url)
                .apply(options)
                .into(faqItemImage)
            faqTitle.text = info.title
            faqDescription.text = info.shortDescription

            binding.root.setOnClickListener {
                onClick(info)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FaqItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(infos[position])
    }

    override fun getItemCount(): Int = infos.size

    fun setData(infos: List<Info>) {
        this.infos.apply {
            clear()
            addAll(infos)
        }
        notifyDataSetChanged()
    }

}