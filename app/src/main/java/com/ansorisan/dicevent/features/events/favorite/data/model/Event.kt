package com.ansorisan.dicevent.features.events.favorite.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val mediaCover: String,
	val summary: String,
	val name: String,
	val beginTime: String,
)