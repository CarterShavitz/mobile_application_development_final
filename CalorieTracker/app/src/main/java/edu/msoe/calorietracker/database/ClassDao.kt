package edu.msoe.calorietracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.calorietracker.Calories
import edu.msoe.calorietracker.Exercise
import edu.msoe.calorietracker.Food
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
@Dao
interface ClassDao {
    @Query("SELECT * FROM food")
    fun getFoods(): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE id=(:id)")
    fun getFood(id: UUID): Food

    @Query("Delete FROM food")
    fun deleteFoods()

    @Query("DELETE FROM food WHERE id=(:id)")
    fun deleteThisFood(id: UUID)

    @Insert
    fun addFood(food: Food)

    @Update
    fun updateFood(food: Food)

    @Query("SELECT * FROM exercise")
    fun getExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE id=(:id)")
    fun getExercise(id: UUID): Exercise

    @Query("Delete FROM exercise")
    fun deleteExercises()

    @Query("DELETE FROM exercise WHERE id=(:id)")
    fun deleteThisExercise(id: UUID)

    @Insert
    fun addExercise(exercise: Exercise)

    @Update
    fun updateExercise(exercise: Exercise)

    @Update
    fun updateCalories(calories: Calories)

    @Query("SELECT * FROM calories WHERE date=(:date)")
    fun getCalories(date: Date): Calories

    @Insert
    fun addCalories(calories: Calories)
}