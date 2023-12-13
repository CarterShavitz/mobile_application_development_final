package edu.msoe.calorietracker

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
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class ViewModel() : ViewModel(){

    private val repository = Repository.get()
    var foods = repository.getFoods()
    var exercises = repository.getExercises()

    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun addFood(food: Food) {
        repository.addFood(food)
    }

    suspend fun addExercise(exercise: Exercise) {
        repository.addExercise(exercise)
    }


}