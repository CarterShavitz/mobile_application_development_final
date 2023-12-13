package edu.msoe.calorietracker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ViewModel() : ViewModel(){

    private val repository = Repository.get()
    var foods = repository.getFoods()
    var exercises = repository.getExercises()
    lateinit var date: Date

    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun setCurrentDate(date: Date) {
        this.date = date
    }
    suspend fun addFood(food: Food) {
        repository.addFood(food)
    }

    suspend fun addExercise(exercise: Exercise) {
        repository.addExercise(exercise)
    }

    suspend fun addCaloriesBurned(calories: Int) {
        addCaloriesBurned(date, calories)
    }
    suspend fun addCaloriesBurned(date: Date, calories: Int) {
        val currentDate = repository.getCalories(date)
        Log.d("viewmodel", date.toString())
        if (currentDate == null) {
            repository.addCalories(Calories(UUID.randomUUID(), date, calories,0))
        } else {
            currentDate.caloriesBurned += calories
            repository.updateCalories(currentDate)
        }
    }

    suspend fun getCalories(date: Date): Calories {
        return repository.getCalories(date)
    }

    suspend fun addCaloriesConsumed(calories: Int) {
        addCaloriesConsumed(date, calories)
    }

    suspend fun addCaloriesConsumed(date: Date, calories: Int) {
        val currentDate = repository.getCalories(date)
        currentDate.caloriesConsumed += calories
        repository.updateCalories(currentDate)
    }

}