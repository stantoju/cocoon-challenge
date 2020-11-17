package com.cocoon.cocoon_challenge.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoryEntity::class], version = 1)
abstract class StoryDB: RoomDatabase(){
    abstract fun storyDao(): StoryDao
}