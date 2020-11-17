package com.cocoon.cocoon_challenge.ui.top_stories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.cocoon.cocoon_challenge.R
import com.cocoon.cocoon_challenge.data.model.Story
import kotlinx.android.synthetic.main.story_item.view.*
import javax.inject.Inject

class StoryAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private var stories: List<Story> = emptyList()
    var onItemClick: ((Story)->Unit) ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        holder.bind(story, position)
    }

    fun setItems(stories: List<Story>) {
        this.stories = stories
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(item: Story, position: Int) {
            v.story_title.text = item.title
            v.story_date.text = item.published_date
            glide.load(item.image)?.into(v.story_img)
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(stories[adapterPosition])
            }
        }
    }
}