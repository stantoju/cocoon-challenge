package com.cocoon.cocoon_challenge.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
    var id: Int?,
    var title: String,
    var abstract: String,
    var url: String,
    var byline: String,
    var published_date: String,
    var image: String,
    var bookmark: Boolean
): Parcelable