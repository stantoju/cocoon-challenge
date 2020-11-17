package com.cocoon.cocoon_challenge.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.cocoon.cocoon_challenge.ui.bookmark.BookmarkAdapter
import com.cocoon.cocoon_challenge.ui.bookmark.BookmarkFragment
import com.cocoon.cocoon_challenge.ui.details.DetailsFragment
import com.cocoon.cocoon_challenge.ui.top_stories.StoriesFragment
import com.cocoon.cocoon_challenge.ui.top_stories.StoryAdapter
import javax.inject.Inject

class MyFragmentFactory @Inject constructor(
    private val storyAdapter: StoryAdapter,
    private val bookmarkAdapter: BookmarkAdapter,
    private val glide: RequestManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            StoriesFragment::class.java.name -> StoriesFragment(storyAdapter)
            BookmarkFragment::class.java.name -> BookmarkFragment(bookmarkAdapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }


}