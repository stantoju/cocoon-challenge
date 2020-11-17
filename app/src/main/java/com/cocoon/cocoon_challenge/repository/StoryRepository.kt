package com.cocoon.cocoon_challenge.repository

import android.util.Log
import com.cocoon.cocoon_challenge.data.local.room.StoryDao
import com.cocoon.cocoon_challenge.data.local.room.StoryEntity
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.data.remote.StoryApi
import com.cocoon.cocoon_challenge.util.DataState
import com.cocoon.cocoon_challenge.util.mapper.StoryCacheMapper
import com.cocoon.cocoon_challenge.util.mapper.StoryEntityMapper
import com.cocoon.cocoon_challenge.util.mapper.StoryNetworkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class StoryRepository
constructor(
    private val storyDao: StoryDao,
    private val storyApi: StoryApi,
    private val storyCacheMapper: StoryCacheMapper,
    private val storyNetworkMapper: StoryNetworkMapper
) {

    /**
     * getStory() function does the following
     * Fetches data from network...
     * If not null or empty, clear non-bookmarked cotent
     * Converts the response networkStory into cacheStory
     * Saves to roomDb through the storyDAO
     * Fetches from DB and converts into the Story domain Model
     * Returns a DataState of List of Story
     */
    suspend fun getStory(): Flow<DataState<List<Story>>> = flow {
        emit(DataState.Loading)

        // Fetch from DB
        var dbStories = storyDao.get()
        emit(DataState.Success(storyCacheMapper.mapFromStoryEntityList(dbStories)))

        // Try fetch from remote
        try {
            val storyResponse = storyApi.fetchStories()
            val remoteStories = storyNetworkMapper.mapFromStoryEntityList(storyResponse.results)
            // Add newly fetched Stories to database only if result isnt null or empty
            if (!remoteStories.isNullOrEmpty()){
                // Delete non-bookmarked items
                storyDao.clearDB()

                // Insert newly fetched items
                for (story in remoteStories){
                    storyDao.insert(storyCacheMapper.mapToEntity(story))
                }

                dbStories = storyDao.get()
            }
            emit(DataState.Success(storyCacheMapper.mapFromStoryEntityList(dbStories)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun getBBookMark(): Flow<DataState<List<Story>>> = flow {
        emit(DataState.Loading)
        val bookmarks = storyDao.fetchBookMarked()
        emit(DataState.Success(storyCacheMapper.mapFromStoryEntityList(bookmarks)))
    }

    suspend fun bookMarkItem(id: Int){
        storyDao.setBookmark(id)
    }


}