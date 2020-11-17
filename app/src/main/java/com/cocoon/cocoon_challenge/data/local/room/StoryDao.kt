package com.cocoon.cocoon_challenge.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storyEntity: StoryEntity)

    @Query("SELECT * FROM stories")
    suspend fun get(): List<StoryEntity>

    @Query("SELECT * FROM stories WHERE bookmark =:bookmark")
    suspend fun fetchBookMarked(bookmark: Boolean = true): List<StoryEntity>

    // Delete only stories that arent bookmarked
    @Query("DELETE FROM stories WHERE bookmark =:bookmark")
    suspend fun clearDB(bookmark: Boolean = false)

    @Query("UPDATE stories SET bookmark =:bookmark  WHERE id =:id")
    suspend fun setBookmark(id:Int, bookmark: Boolean = true)


}