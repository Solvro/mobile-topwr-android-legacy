package com.solvro.topwr.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.core.api.model.departments.FieldsOfStudyRemote
import com.solvro.topwr.databinding.FieldOfStudyItemBinding

class FieldsOfStudyAdapter(
    private val fieldOfStudy: MutableList<FieldsOfStudyRemote>
) : RecyclerView.Adapter<FieldsOfStudyAdapter.ViewHolder>() {

    inner class ViewHolder(binding: FieldOfStudyItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        private val fieldOfStudyName = binding.fieldOfStudyText
        fun bind(){
            fieldOfStudyName.text = fieldOfStudy[adapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FieldOfStudyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldsOfStudyAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = fieldOfStudy.size

    fun updateList(newList: List<FieldsOfStudyRemote>){
        fieldOfStudy.clear()
        fieldOfStudy.addAll(newList)
        notifyDataSetChanged()
    }

}