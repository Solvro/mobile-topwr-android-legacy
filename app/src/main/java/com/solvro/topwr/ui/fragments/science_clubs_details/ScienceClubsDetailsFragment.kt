package com.solvro.topwr.ui.fragments.science_clubs_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.databinding.ScienceClubsDetailsFragmentBinding

class ScienceClubsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ScienceClubsDetailsFragment()
    }

    private lateinit var binding: ScienceClubsDetailsFragmentBinding
    private lateinit var viewModel: ScienceClubsDetailsViewModel
    private val contactsAdapter = ContactsAdapter {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScienceClubsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setObservers()
    }

    private fun setObservers() {
        viewModel.scienceClub.observe(viewLifecycleOwner) {
            bindScienceClub(it)
        }
    }

    private fun setupRecyclerView() {
        binding.scienceClubDetailContactsList.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindScienceClub(scienceClub: ScienceClub) {
        binding.apply {
            scienceClubName.text = scienceClub.name
            scienceClubDepartment.text = scienceClub.department.toString()
            scienceClubDetailAboutText.text = scienceClub.description
            contactsAdapter.setData(scienceClub.infoSection?.first()?.info ?: listOf())
            Glide.with(requireContext())
                .load(scienceClub.backgroundPhoto?.url)
                .into(scienceClubBackgroundImage)
            Glide.with(requireContext())
                .load(scienceClub.photo?.url)
                .into(scienceClubDetailFragmentLogo)
        }
    }
}