package edu.msoe.calorietracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Exercise (
    @PrimaryKey val id: UUID,
    val name: String,
    val calories: Int,
    val duration: Int
)