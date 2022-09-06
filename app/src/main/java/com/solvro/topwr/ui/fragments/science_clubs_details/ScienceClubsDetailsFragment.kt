package com.solvro.topwr.ui.fragments.science_clubs_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.databinding.ScienceClubsDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScienceClubsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ScienceClubsDetailsFragment()
    }

    private lateinit var binding: ScienceClubsDetailsFragmentBinding
    private val viewModel: ScienceClubsDetailsViewModel by viewModels()
    private val contactsAdapter = ContactsAdapter({
        navigateToWebsite(it)
    }, { navigateToEmail(it) })

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
        setNavigateBackBtn()
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

    private fun setNavigateBackBtn() {
        binding.scienceClubsBackToMainBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToWebsite(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    private fun navigateToEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, email)
        }
        startActivity(intent)
    }
}