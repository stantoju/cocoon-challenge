package com.cocoon.cocoon_challenge.util.mapper

import com.cocoon.cocoon_challenge.data.local.room.StoryEntity
import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.data.remote.StoryNetworkEntity
import javax.inject.Inject

class StoryCacheMapper
@Inject
constructor(): StoryEntityMapper<StoryEntity, Story>
{
    override fun mapFromEntity(entity: StoryEntity): Story {
        return Story(
            id = entity.id,
            title = entity.title,
            abstract = entity.details,
            url = entity.url,
            byline = entity.byline,
            published_date = entity.published_date,
            image = entity.image,
            bookmark = entity.bookmark
        )
    }

    override fun mapToEntity(domainModel: Story): StoryEntity {
        return StoryEntity(
            id = 0,
            title = domainModel.title,
            details = domainModel.abstract,
            url = domainModel.url,
            byline = domainModel.byline,
            published_date = domainModel.published_date,
            image = domainModel.image,
            bookmark = domainModel.bookmark
        )
    }

    fun mapFromStoryEntityList(entities: List<StoryEntity>): List<Story>{
        return entities.map { mapFromEntity(it) }
    }
}