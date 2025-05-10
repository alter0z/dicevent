package com.ansorisan.dicevent.features.events.favorite.domain.usecase

import com.ansorisan.dicevent.features.events.favorite.domain.repository.Event
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(private val repository: Event) {
    suspend operator fun invoke(id: Int) = repository.getEventById(id)
}