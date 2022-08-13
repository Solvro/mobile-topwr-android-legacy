package com.solvro.topwr.ui.fragments.science_clubs_page

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.databinding.DialogItemFilterBinding
import com.solvro.topwr.databinding.DialogScienceClubsTagBinding


class TagsDialog(private val tags: List<String>, private val onClick: (String?) -> Unit) :
    DialogFragment() {

    private lateinit var binding: DialogScienceClubsTagBinding
    private val adapter = TagsAdapter {
        onClick.invoke(it)
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogScienceClubsTagBinding.inflate(LayoutInflater.from(context))
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(
                binding.root
            )
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()
        setOnClickListeners()
        binding.filterRecyclerView.apply {
            adapter = this@TagsDialog.adapter
            layoutManager = LinearLayoutManager(context)
        }
        adapter.setData(tags)
    }

    private fun setOnClickListeners() {
        binding.clearFiltersButton.setOnClickListener {
            onClick(null)
            dismiss()
        }
    }
}

class TagsAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    private val tags = mutableListOf<String>()

    inner class ViewHolder(private val binding: DialogItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            binding.tagTextView.text = tag
            binding.root.setOnClickListener {
                onClick.invoke(tag)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DialogItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tags[position])

    override fun getItemCount(): Int = tags.size

    fun setData(tags: List<String>) {
        this.tags.clear()
        this.tags.addAll(tags)
        notifyDataSetChanged()
    }
}