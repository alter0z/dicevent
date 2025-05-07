package com.ansorisan.dicevent.base.domain.usecases

import com.ansorisan.dicevent.base.domain.repositories.Event
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(private val repository: Event) {
    suspend operator fun invoke(active: Int) = repository.getEvents(active)
}