package com.cocoon.cocoon_challenge.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StoryNetworkEntity(

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("abstract")
    @Expose
    var abstract: String,

    @SerializedName("url")
    @Expose
    var url: String,

    @SerializedName("byline")
    @Expose
    var byline: String,

    @SerializedName("published_date")
    @Expose
    var published_date: String,

    @SerializedName("multimedia")
    @Expose
    var multimedia: List<MultimediaNetworkEntity>

)