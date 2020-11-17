package com.cocoon.cocoon_challenge.di


import com.cocoon.cocoon_challenge.data.local.room.StoryDao
import com.cocoon.cocoon_challenge.data.remote.StoryApi
import com.cocoon.cocoon_challenge.repository.StoryRepository
import com.cocoon.cocoon_challenge.util.mapper.StoryCacheMapper
import com.cocoon.cocoon_challenge.util.mapper.StoryNetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyDao: StoryDao,
        storyApi: StoryApi,
        storyCacheMapper: StoryCacheMapper,
        storyNetworkMapper: StoryNetworkMapper
    ): StoryRepository {
        return StoryRepository(storyDao, storyApi,storyCacheMapper,storyNetworkMapper)
    }

}