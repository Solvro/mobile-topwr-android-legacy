package com.solvro.topwr.features.scienceclub.presentation.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ScienceClubsDetailsFragmentBinding
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
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
        setupSharedElementTransition()
        setObservers()
        setNavigateBackBtn()
    }

    private fun setObservers() {
        viewModel.apply {
            scienceClub.observe(viewLifecycleOwner) {
                bindScienceClub(it)
            }
            departmentName.observe(viewLifecycleOwner) {
                binding.scienceClubDepartment.text = it ?: getString(R.string.unknown_department)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.scienceClubDetailContactsList.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSharedElementTransition() {
        binding.apply {
            scienceClubName.transitionName = "science_club_name"
            scienceClubDetailFragmentLogo.transitionName = "science_club_logo"
            sharedElementEnterTransition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.move)
            sharedElementReturnTransition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.move)
        }
    }

    private fun bindScienceClub(scienceClub: ScienceClub) {
        binding.apply {
            scienceClubName.text = scienceClub.name
            scienceClubDetailAboutText.text = scienceClub.description
            contactsAdapter.setData(scienceClub.infoSection?.first()?.info ?: listOf())
            Glide.with(requireContext())
                .load(scienceClub.backgroundPhoto?.url)
                .into(scienceClubBackgroundImage)
            Glide.with(requireContext())
                .load(scienceClub.photo?.url)
                .circleCrop()
                .into(scienceClubDetailFragmentLogo)
        }
    }

    private fun setNavigateBackBtn() {
        binding.scienceClubsBackToMainBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun navigateToWebsite(urlString: String) {
        val url = if (urlString.startsWith("https://") || urlString.startsWith("http://"))
            urlString else "http://$urlString"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        try {
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.cant_navigate_to_browser, Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun navigateToEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${email}")
            putExtra(Intent.EXTRA_EMAIL, email)
        }
        startActivity(intent)
    }
}