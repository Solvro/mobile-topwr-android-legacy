package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.solvro.topwr.data.model.info.Infos
import com.solvro.topwr.databinding.FaqItemBinding
import com.solvro.topwr.databinding.WhatsUpItemBinding

class FaqAdapter(
    private val infos: List<Infos>,
    private val onClick: (Infos) -> Unit
) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FaqItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            //set OnCLickListener and send data with function
            binding.root.setOnClickListener {
                onClick(infos[adapterPosition])
            }
        }

        private val faqItemImage = binding.imageView
        private val faqTitle = binding.textViewTitle
        private val faqDescription = binding.textViewDescription
        fun bind() {
            val options: RequestOptions = RequestOptions().centerCrop().transform(
                CenterCrop(),
                GranularRoundedCorners (8F,
                    8F,
                    0F,
                    0F
                )
            )

            Glide.with(faqItemImage)
                .load(infos[adapterPosition].photo?.url)
                .apply(options)
                .into(faqItemImage)
            faqTitle.text = infos[adapterPosition].title
            faqDescription.text = infos[adapterPosition].description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqAdapter.ViewHolder {
        val binding = FaqItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FaqAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = infos.size

}