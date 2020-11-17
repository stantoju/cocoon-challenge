package com.cocoon.cocoon_challenge.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MultimediaNetworkEntity (

    @SerializedName("url")
    @Expose
    var url: String
)