package com.moscow.mapper

import com.android.domain.model.Actor
import com.moscow.local.entity.ActorEntity
import com.moscow.local.entity.Gender
import kotlin.collections.map

fun List<ActorEntity>.toDomain(): List<Actor> {
    return map { actorEntity ->
        Actor(
            id = actorEntity.id,
            name = actorEntity.name,
            profileImg = actorEntity.profileImg,
            gender = if (actorEntity.gender == Gender.MALE) com.android.domain.model.Gender.MALE else com.android.domain.model.Gender.FEMALE
        )
    }
}

fun List<Actor>.toEntity(searchTerm: String): List<ActorEntity> {
    return map { actor ->
        ActorEntity(
            id = actor.id,
            name = actor.name,
            profileImg = actor.profileImg,
            searchTerm = searchTerm,
            gender = if (actor.gender == com.android.domain.model.Gender.MALE) Gender.MALE else Gender.FEMALE
        )
    }
}