package com.cocoon.cocoon_challenge.util.mapper

import com.cocoon.cocoon_challenge.data.model.Story
import com.cocoon.cocoon_challenge.data.remote.MultimediaNetworkEntity
import com.cocoon.cocoon_challenge.data.remote.StoryNetworkEntity
import javax.inject.Inject


class StoryNetworkMapper
@Inject

constructor(): StoryEntityMapper<StoryNetworkEntity, Story> {

    override fun mapFromEntity(entity: StoryNetworkEntity): Story {
        return Story(
            id = null,
            title = entity.title,
            abstract = entity.abstract,
            url = entity.url,
            byline = entity.byline,
            published_date = entity.published_date,
            image = extractImage(entity.multimedia),
            bookmark =  false
        )
    }

    override fun mapToEntity(domainModel: Story): StoryNetworkEntity {
        return StoryNetworkEntity(
            title = domainModel.title,
            abstract = domainModel.abstract,
            url = domainModel.url,
            byline = domainModel.byline,
            published_date = domainModel.published_date,
            multimedia = generateMultiMedia(domainModel.image)
        )
    }


    fun mapFromStoryEntityList(entities: List<StoryNetworkEntity>): List<Story>{
        return entities.map { mapFromEntity(it) }
    }



    /**
     * Extract the normal image item from multimedia array
     * in - List of Multimedila
     * out - String
     */
    private fun extractImage(multimediaList: List<MultimediaNetworkEntity>): String {
        if (multimediaList.isEmpty() || multimediaList == null) {
            return ""
        }
        return multimediaList[multimediaList.size - 1].url
    }


    /**
     * Generate Multimedia List by passing in 1 url string
     * in - List of String
     * out - Multimedia
     */
    private fun generateMultiMedia(url: String): List<MultimediaNetworkEntity> {
        val multimediaArray =  ArrayList<MultimediaNetworkEntity>()
        val multimediaNetworkEntity = MultimediaNetworkEntity(url)
        multimediaArray.add(multimediaNetworkEntity)
        return multimediaArray
    }



}