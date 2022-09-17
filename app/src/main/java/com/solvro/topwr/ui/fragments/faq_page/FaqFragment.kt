package com.solvro.topwr.ui.fragments.faq_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.solvro.topwr.BuildConfig
import com.solvro.topwr.R
import com.solvro.topwr.data.model.aboutUs.AboutUs
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.FaqFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FaqFragment : Fragment() {

    private val viewModel: FaqViewModel by viewModels()
    private val faqAdapter = FaqAdapter { info, imageView ->
        navigateToDetails(info, imageView)
    }
    private val aboutUsAdapter = AboutUsAdapter { aboutUs, imageView ->
        navigateToAboutUs(aboutUs, imageView)
    }
    private lateinit var binding: FaqFragmentBinding
    private var searchViewJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FaqFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setObservers()
        setVersion()
    }

    private fun setObservers() {
        viewModel.infos.observe(viewLifecycleOwner) {
            it.data?.let { infosData ->
                faqAdapter.setData(infosData)
            }
        }

        viewModel.aboutUs.observe(viewLifecycleOwner) {
            aboutUsAdapter.setData(it)
        }
    }

    private fun setupRecyclerView() {
        val concatAdapter = ConcatAdapter(aboutUsAdapter, faqAdapter)
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }
    }

    private fun setupSearchView() {
        binding.faqSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(text: String?): Boolean {
                searchViewJob?.cancel()
                searchViewJob = lifecycleScope.launch {
                    delay(500)
                    viewModel.setTextFilter(text ?: "")
                }
                return false
            }

        })
    }

    private fun navigateToDetails(info: Info, imageView: ImageView) {
        val extras = FragmentNavigator.Extras.Builder().addSharedElements(
            mapOf(
                imageView to getString(R.string.faq_image, info.id),
            )
        ).build()

        val action = FaqFragmentDirections.actionFaqFragmentToFaqDetailsFragment(info)
        findNavController().navigate(action, extras)
    }

    private fun navigateToAboutUs(aboutUs: AboutUs, imageView: ImageView) {
        val extras = FragmentNavigator.Extras.Builder().addSharedElements(
            mapOf(
                imageView to getString(R.string.about_us_image),
            )
        ).build()

        val action = FaqFragmentDirections.actionFaqFragmentToAboutUsFragment(aboutUs)
        findNavController().navigate(action, extras)
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }

    private fun setVersion(){
        binding.versionTextView.text = getString(R.string.version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}