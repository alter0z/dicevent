package com.ansorisan.dicevent.features.events.favorite.domain.usecase

import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event
import com.ansorisan.dicevent.features.events.favorite.domain.repository.Event as Repo
import javax.inject.Inject

class InsertEventsUseCase @Inject constructor(private val repository: Repo) {
    suspend operator fun invoke(event: Event) = repository.insertEvent(event)
}