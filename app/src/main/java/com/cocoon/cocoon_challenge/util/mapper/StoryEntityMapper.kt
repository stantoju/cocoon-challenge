package com.cocoon.cocoon_challenge.util.mapper


interface StoryEntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}