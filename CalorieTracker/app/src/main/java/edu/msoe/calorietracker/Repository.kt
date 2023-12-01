package edu.msoe.calorietracker

import android.content.Context
import androidx.room.Room
import edu.msoe.calorietracker.database.Database
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "database"

class Repository private constructor(
    context: Context,
) {
    private val database: Database = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).build()

    fun getExercises(): Flow<List<Exercise>> = database.classDao().getExercises()
    suspend fun getExercise(id: UUID): Exercise = database.classDao().getExercise(id)
    suspend fun addExercise(exercise: Exercise) = database.classDao().addExercise(exercise)
    suspend fun deleteExercises() = database.classDao().deleteExercises()
    suspend fun deleteThisExercise(id: UUID) = database.classDao().deleteThisExercise(id)
    suspend fun updateExercise(exercise: Exercise) {
        database.classDao().updateExercise(exercise)
    }

    fun getFoods(): Flow<List<Food>> = database.classDao().getFoods()
    suspend fun getFood(id: UUID): Food = database.classDao().getFood(id)
    suspend fun addFood(food: Food) = database.classDao().addFood(food)
    suspend fun deleteFoods() = database.classDao().deleteFoods()
    suspend fun deleteThisFood(id: UUID) = database.classDao().deleteThisFood(id)
    suspend fun updateFood(food: Food) {
        database.classDao().updateFood(food)
    }

    companion object {
        private var INSTANCE: Repository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Repository(context)
            }
        }

        fun get(): Repository {
            return INSTANCE?:
            throw IllegalStateException("FoodRepository must be initialized")
        }
    }
}