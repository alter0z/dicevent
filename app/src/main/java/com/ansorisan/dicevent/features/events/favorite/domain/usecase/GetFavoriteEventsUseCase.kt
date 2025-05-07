package com.ansorisan.dicevent.features.events.favorite.domain.usecase

import com.ansorisan.dicevent.features.events.favorite.domain.repository.Event
import javax.inject.Inject

class GetFavoriteEventsUseCase @Inject constructor(private val repository: Event) {
    suspend operator fun invoke() = repository.getAllEvents()
}