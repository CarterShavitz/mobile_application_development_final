package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        fun onGoFrontFragment()
    }

    private var listener: OnFragmentInteractionListener? = null

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
        val goAddExercise: Button = view.findViewById(R.id.add_exercise)

        // Set click listeners for the buttons
        goToUniqueExerciseButton.setOnClickListener {
            listener?.onGoToUniqueExerciseButtonClick()
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        goAddExercise.setOnClickListener {
            listener?.onGoFrontFragment()
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Exercise", "Start coroutine")

            var exercises: List<Exercise> = emptyList()
            viewModel.exercises.collect { exerciseList ->
                exercises = exerciseList
                val exerciseNames = ArrayList<String>()
                withContext(Dispatchers.Main) {
                    Log.d("Exercise", "Main context")
                    for (exercise: Exercise in exercises) {
                        exerciseNames.add(exercise.name)
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

                                val exercise = exercises[position]
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
