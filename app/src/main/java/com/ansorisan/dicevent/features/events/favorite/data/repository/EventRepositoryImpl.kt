package com.ansorisan.dicevent.features.events.favorite.data.repository

import com.ansorisan.dicevent.features.events.favorite.data.data_source.EventDao
import com.ansorisan.dicevent.features.events.favorite.data.mapper.toEntity
import com.ansorisan.dicevent.features.events.favorite.data.mapper.toModel
import com.ansorisan.dicevent.features.events.favorite.domain.entity.Event
import com.ansorisan.dicevent.features.events.favorite.domain.repository.Event as Repo
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val dao: EventDao) : Repo {

    override suspend fun getAllEvents(): List<Event> =
        dao.getAll().map { it.toEntity() }

//    override suspend fun getNoteById(id: Int): Note? =
//        dao.getById(id)?.toDomain()

    override suspend fun insertEvent(event: Event) =
        dao.insert(event.toModel())

//    override suspend fun updateNote(note: Note) =
//        dao.update(note.toEntity())

    override suspend fun deleteEvent(event: Event) =
        dao.delete(event.toModel())
}