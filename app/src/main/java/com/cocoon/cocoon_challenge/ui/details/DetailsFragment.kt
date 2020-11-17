package com.cocoon.cocoon_challenge.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.cocoon.cocoon_challenge.R
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.ui.top_stories.StoryViewmodel
import com.cocoon.cocoon_challenge.util.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment @Inject constructor(
    private val glide: RequestManager,
    var viewModel: StoryViewmodel? = null
)  : Fragment() {

    lateinit var story: Story

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(StoryViewmodel::class.java)

        /**
         * Fetch the parsed argument
         * Type: Story
         * which could either be either of the arguments (story, bookmark)
         */
        var args = DetailsFragmentArgs.fromBundle(requireArguments())

        story = if(args.story != null) {
            args.story!!
        } else {
            args.bookmark!!
        }

        subscribeToObservers()
        setonclickListerners()

        // Populate the fields with story properties
        details_title.text = story.title
        details_abstract.text = story.abstract
        details_date.text = story.published_date
        details_url.text = story.url
        glide.load(story.image)?.into(details_image)
    }

    private fun setonclickListerners() {
        details_fab.setOnClickListener {
            viewModel!!.setBookmark(story.id!!)
        }

        details_url.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(story.url))
            startActivity(intent)
        }
    }


    private fun subscribeToObservers() {
        viewModel?.bookmarked?.observe(viewLifecycleOwner, Observer {
            if (it){
                details_coord.snackBar("Story Liked/Bookmarked")
                viewModel!!.reverseBookmarkLiveData()

            }
        })
    }


}