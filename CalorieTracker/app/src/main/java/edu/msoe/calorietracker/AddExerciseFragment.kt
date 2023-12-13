package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class AddExerciseFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()

    companion object {
        fun newInstance(): AddExerciseFragment {
            return AddExerciseFragment()
        }
    }

    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onGoToUniqueExerciseButtonClick()
        fun onGoBackHomeButtonClick()
        fun onGoFrontFragmentWithBurnedCalories(burnedCalories: Int)
    }

    private var listener: OnFragmentInteractionListener? = null

    // Mutex for synchronizing access to the exercises list
    private val exercisesMutex = Mutex()
    private var exercises: List<Exercise> = emptyList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.add_exercise_page, container, false)

        // Find buttons by their IDs
        val goToUniqueExerciseButton: Button = view.findViewById(R.id.go_to_unique_exercise_button)
        val goBackButton: Button = view.findViewById(R.id.go_back)

        // Set click listeners for the buttons
        goToUniqueExerciseButton.setOnClickListener {
            listener?.onGoToUniqueExerciseButtonClick()
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        // Replace "add_exercise_button" with the actual ID of your button
        val addExerciseButton: Button = view.findViewById(R.id.add_exercise)

        addExerciseButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                // Use the mutex to ensure proper synchronization
                exercisesMutex.withLock {
                    val spinner: Spinner = view.findViewById(R.id.common_exercise_spinner)
                    val selectedPosition = spinner.selectedItemPosition
                    val selectedExercise = if (selectedPosition != AdapterView.INVALID_POSITION) {
                        exercises[selectedPosition]
                    } else {
                        // Handle the case where no exercise is selected
                        null
                    }

                    listener?.onGoFrontFragmentWithBurnedCalories(selectedExercise?.calories ?: 0)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Exercise", "Start coroutine")

            viewModel.exercises.collect { exerciseList ->
                // Use the mutex to ensure proper synchronization
                exercisesMutex.withLock {
                    exercises = exerciseList
                }

                val exerciseNames = ArrayList<String>()
                withContext(Dispatchers.Main) {
                    Log.d("Exercise", "Main context")
                    // Use the mutex to ensure proper synchronization
                    exercisesMutex.withLock {
                        for (exercise: Exercise in exercises) {
                            exerciseNames.add(exercise.name)
                        }
                    }

                    if (isAdded) {
                        val spinner: Spinner = view.findViewById(R.id.common_exercise_spinner)
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            exerciseNames
                        )
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                // You can use 'exercise' as needed, e.g., exercise.calories
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // Do nothing
                            }
                        }
                    } else {
                        Log.d("Exercise", "Fragment not attached yet, delay")
                    }
                }
            }
        }
        return view
    }
}
