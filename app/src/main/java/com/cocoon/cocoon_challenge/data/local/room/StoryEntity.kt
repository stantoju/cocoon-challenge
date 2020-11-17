package com.cocoon.cocoon_challenge.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stories")
data class StoryEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "details")
    var details: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "byline")
    var byline: String,

    @ColumnInfo(name = "published_date")
    var published_date: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "bookmark")
    var bookmark: Boolean = false
){
}
