package com.ansorisan.dicevent.base.data.mapper

import com.ansorisan.dicevent.base.domain.entities.Event as Entity
import com.ansorisan.dicevent.base.data.models.Event as Model

fun Model.toEntity(): Entity {
    return Entity(
        summary = this.summary,
        mediaCover = this.mediaCover,
        registrants = this.registrants,
        imageLogo = this.imageLogo,
        link = this.link,
        description = this.description,
        ownerName = this.ownerName,
        cityName = this.cityName,
        quota = this.quota,
        name = this.name,
        id = this.id,
        beginTime = this.beginTime,
        endTime = this.endTime,
        category = this.category
    )
}