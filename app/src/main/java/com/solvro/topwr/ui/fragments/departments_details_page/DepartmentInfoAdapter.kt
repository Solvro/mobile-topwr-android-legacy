package com.solvro.topwr.ui.fragments.departments_details_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.solvro.topwr.data.model.departments.Info
import com.solvro.topwr.databinding.ItemInfoBinding

class DepartmentInfoAdapter(
    private val onPhoneNumberClick: (String) -> Unit
) : RecyclerView.Adapter<DepartmentInfoAdapter.ViewHolder>() {

    private val data = mutableListOf<Info>()

    inner class ViewHolder(private val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info) {
            with(binding) {
                Glide.with(infoIcon)
                    .load(info.icon?.url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(infoIcon)
                infoText.apply {
                    text = info.value
                    if (info.type == "PhoneNumber") {
                        setOnClickListener { info.value?.let { onPhoneNumberClick(it) } }
                    } else setOnClickListener {}
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.count()

    fun addData(newData: List<Info>) {
        if (data.isNotEmpty())
            data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}