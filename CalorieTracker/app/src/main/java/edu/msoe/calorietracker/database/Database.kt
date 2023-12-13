package edu.msoe.calorietracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.msoe.calorietracker.Exercise
import edu.msoe.calorietracker.Food
import edu.msoe.calorietracker.Calories

@Database(entities = [Exercise::class, Food::class, Calories::class], version=1)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun classDao(): ClassDao
}