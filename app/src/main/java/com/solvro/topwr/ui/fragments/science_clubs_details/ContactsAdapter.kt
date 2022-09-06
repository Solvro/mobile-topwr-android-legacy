package com.solvro.topwr.ui.fragments.science_clubs_details

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.R
import com.solvro.topwr.data.model.scienceClub.Info
import com.solvro.topwr.databinding.ItemContactBinding

class ContactsAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private val data = mutableListOf<Info>()

    inner class ViewHolder(
        private val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(info: Info) {
            binding.contactIcon.setImageDrawable(
                binding.root.context.getDrawable(
                    when (info.getType()) {
                        Info.InfoType.WEBSITE -> R.drawable.ic_website
                        Info.InfoType.FACEBOOK -> R.drawable.ic_facebook
                        Info.InfoType.EMAIL -> R.drawable.ic_message
                    }
                )
            )
            binding.contactText.apply {
                text = info.visibleText
                movementMethod = LinkMovementMethod.getInstance()
            }
            binding.root.setOnClickListener {
                onClick.invoke(info.value)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int = data.count()

    fun setData(newData: List<Info>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }
}