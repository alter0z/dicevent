package com.ansorisan.dicevent.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ansorisan.dicevent.features.events.favorite.data.data_source.EventDao
import com.ansorisan.dicevent.features.events.favorite.data.model.Event

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}