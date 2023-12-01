package edu.msoe.calorietracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.msoe.calorietracker.Exercise
import edu.msoe.calorietracker.Food

@Database(entities = [Exercise::class, Food::class], version=1)
abstract class Database : RoomDatabase() {
    abstract fun classDao(): ClassDao
}