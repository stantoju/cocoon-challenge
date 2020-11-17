package com.cocoon.cocoon_challenge.data.remote.response

import com.cocoon.cocoon_challenge.data.remote.StoryNetworkEntity

class StoryResponse(
    val status: String,
    val copyright: String,
    val section: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<StoryNetworkEntity>
    )