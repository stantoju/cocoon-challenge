package com.cocoon.cocoon_challenge.di

import android.content.Context
import androidx.room.Room
import com.cocoon.cocoon_challenge.data.local.room.StoryDB
import com.cocoon.cocoon_challenge.data.local.room.StoryDao
import com.cocoon.cocoon_challenge.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideStoryDB(@ApplicationContext context: Context): StoryDB {
        return Room.databaseBuilder(
            context,
            StoryDB::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideStoryDAO(storyDB: StoryDB): StoryDao {
        return storyDB.storyDao()
    }


}