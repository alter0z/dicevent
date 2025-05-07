package com.ansorisan.dicevent.features.events.favorite.data.mapper

import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event as Entity
import com.ansorisan.dicevent.features.events.favorite.data.model.Event as Model

fun Model.toEntity(): Entity {
    return Entity(
        summary = this.summary,
        mediaCover = this.mediaCover,
        name = this.name,
        id = this.id,
        beginTime = this.beginTime,
    )
}

fun Entity.toModel(): Model {
    return Model(
        summary = this.summary,
        mediaCover = this.mediaCover,
        name = this.name,
        id = this.id,
        beginTime = this.beginTime,
    )
}