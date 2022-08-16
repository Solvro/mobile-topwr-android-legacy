package com.solvro.topwr.ui.fragments.science_clubs_page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solvro.topwr.databinding.ItemLoadstateBinding
import com.solvro.topwr.utils.gone
import com.solvro.topwr.utils.visible

class ScienceClubsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ScienceClubsLoadStateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLoadstateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.loadStateItemRetryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                when (loadState) {
                    is LoadState.Loading -> {
                        loadStateItemErrorLayout.gone()
                        loadStateItemLoadingLayout.visible()
                    }
                    is LoadState.Error -> {
                        loadStateItemErrorLayout.visible()
                        loadStateItemLoadingLayout.gone()
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val binding =
            ItemLoadstateBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}