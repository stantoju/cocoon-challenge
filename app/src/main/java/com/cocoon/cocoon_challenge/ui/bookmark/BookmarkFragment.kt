package com.cocoon.cocoon_challenge.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cocoon.cocoon_challenge.R
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.ui.home.HomeFragmentDirections
import com.cocoon.cocoon_challenge.ui.top_stories.BookmarkStateEvent
import com.cocoon.cocoon_challenge.ui.top_stories.StoryAdapter
import com.cocoon.cocoon_challenge.ui.top_stories.StoryStateEvent
import com.cocoon.cocoon_challenge.ui.top_stories.StoryViewmodel
import com.cocoon.cocoon_challenge.util.Constants.GRID_SPAN_COUNT
import com.cocoon.cocoon_challenge.util.DataState
import com.cocoon.cocoon_challenge.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_stories.*
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment @Inject constructor(
    private val bookmarkAdapter: BookmarkAdapter,
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
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(StoryViewmodel::class.java)


    }

    private fun setupRecyclerView() {
        bookmark_rv.apply {
            adapter = bookmarkAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }

    private fun subscribeToObservers() {
        viewModel?.bookmarkDataState?.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Story>> -> {
                    displayProgress(false)
                    bookmarkAdapter.setItems(dataState.data)
                    // OnItemclick listener to setup navigation to details fragment
                    bookmarkAdapter.onItemClick = {
                        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(null,it))
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
        bookmark_swipe_refresh.isRefreshing = isDisplayed
    }

    override fun onStart() {
        super.onStart()

        subscribeToObservers()
        setupRecyclerView()

        viewModel!!.setBookmarkStateEvent(BookmarkStateEvent.GetBookmarkStateEvent)

        bookmark_swipe_refresh.setOnRefreshListener {
            viewModel!!.setBookmarkStateEvent(BookmarkStateEvent.GetBookmarkStateEvent)
        }
    }


}