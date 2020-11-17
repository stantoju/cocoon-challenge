package com.cocoon.cocoon_challenge.ui.top_stories

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cocoon.cocoon_challenge.R
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.ui.home.HomeFragmentDirections
import com.cocoon.cocoon_challenge.util.DataState
import com.cocoon.cocoon_challenge.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stories.*
import javax.inject.Inject

@AndroidEntryPoint
class StoriesFragment @Inject constructor(
     val storyAdapter: StoryAdapter,
     var viewModel: StoryViewmodel? = null
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(StoryViewmodel::class.java)


    }

    private fun setupRecyclerView() {
        stories_rv.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /**
     * Setup observer behaviour for the 3 options of datastate
     * 1. Onsuccess: pupulate the stories to the adapter and setup on itemclick listener
     * 2. OnError: display the error message
     * 3. OnLoding: display the progress indicator
     */

    private fun subscribeToObservers() {
        viewModel?.storyDataState?.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Story>> -> {
                    displayProgress(false)
                    storyAdapter.setItems(dataState.data)

                    // OnItemclick listener to setup navigation to details fragment
                    storyAdapter.onItemClick = {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(it, null))
                    }
                }
                is DataState.Error -> {
                    displayProgress(false)
                    displayError(dataState.exception.message)
                }

                is DataState.Loading -> {
                    displayProgress(true)
                }
            }
        })

    }

    private fun displayError(message: String?){
        if (message != null) {
            requireActivity().toast(message)
        }
    }


    private fun displayProgress(isDisplayed: Boolean){
        story_swipe_refresh.isRefreshing = isDisplayed
    }

    override fun onStart() {
        super.onStart()
        subscribeToObservers()
        setupRecyclerView()
        viewModel!!.setStoryStateEvent(StoryStateEvent.GetStoriesEvent)

        story_swipe_refresh.setOnRefreshListener {
            viewModel!!.setStoryStateEvent(StoryStateEvent.GetStoriesEvent)
        }
    }

}