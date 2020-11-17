package com.cocoon.cocoon_challenge.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cocoon.cocoon_challenge.R
import com.cocoon.cocoon_challenge.ui.bookmark.BookmarkFragment
import com.cocoon.cocoon_challenge.ui.details.DetailsFragment
import com.cocoon.cocoon_challenge.ui.top_stories.StoriesFragment
import com.cocoon.cocoon_challenge.util.MyFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var fragmentFactory: MyFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
    }

    private fun setup() {
        val adapter = HomeAdapter(childFragmentManager)

        // Create StoriesFragment with Custom Fragment Factory
        val storiesFragment = requireActivity().supportFragmentManager.fragmentFactory.instantiate(
            StoriesFragment::class.java.classLoader!!,
            StoriesFragment::class.java.name)

        // Create BookmarkFragment with Custom Fragment Factory
        val bookmarkFragment = requireActivity().supportFragmentManager.fragmentFactory.instantiate(
            BookmarkFragment::class.java.classLoader!!,
            BookmarkFragment::class.java.name)

        adapter.addFragment(storiesFragment, "Top Stories")
        adapter.addFragment(bookmarkFragment, "Bookmarks")

        mainViewPager.adapter = adapter
        mainTabLayout.setupWithViewPager(mainViewPager)

    }


}