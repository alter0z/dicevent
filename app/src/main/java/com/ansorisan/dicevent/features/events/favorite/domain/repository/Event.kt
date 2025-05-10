package com.ansorisan.dicevent.features.events.favorite.domain.repository

import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event

interface Event {
    suspend fun getAllEvents(): List<Event>
    suspend fun getEventById(id: Int): Event?
    suspend fun insertEvent(event: Event)
    suspend fun deleteEvent(event: Event)
}