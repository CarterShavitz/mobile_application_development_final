package edu.msoe.calorietracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Calories (
    @PrimaryKey val id: UUID,
    val date: Date,
    var caloriesBurned: Int = 0,
    var caloriesConsumed: Int = 0
)