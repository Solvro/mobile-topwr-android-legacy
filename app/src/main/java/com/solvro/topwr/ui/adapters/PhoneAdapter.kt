package com.solvro.topwr.ui.adapters

import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.databinding.ItemInfoBinding

class PhoneAdapter(
    private val phoneNumbers: List<String>,
    private val onClick: (String) -> Unit = {}
) : RecyclerView.Adapter<PhoneAdapter.PhoneNumbersViewHolder>() {

    inner class PhoneNumbersViewHolder(private val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val phoneIcon = binding.infoIcon
        private val phoneNumberText = binding.infoText

        fun bind() {
            phoneNumberText.autoLinkMask = Linkify.PHONE_NUMBERS
            phoneNumberText.text = phoneNumbers[adapterPosition]
            phoneNumberText.movementMethod = LinkMovementMethod.getInstance()

            phoneNumberText.setOnClickListener {
                onClick(phoneNumbers[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneNumbersViewHolder {
        val binding = ItemInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhoneNumbersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhoneNumbersViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = phoneNumbers.size
}