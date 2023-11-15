package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText

class AddUniqueExerciseFragment : Fragment() {

    companion object {
        fun newInstance(): AddUniqueExerciseFragment {
            return AddUniqueExerciseFragment()
        }
    }

    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onAddExerciseButtonClick(exerciseName: String, calorieBurned: String, duration: String)
        fun onGoBackExerciseButtonClick()
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
        val view: View = inflater.inflate(R.layout.add_unique_exercise, container, false)

        // Find views by their IDs
        val exerciseNameEditText: EditText = view.findViewById(R.id.exercise_name_edit_text)
        val calorieBurnedEditText: EditText = view.findViewById(R.id.calorie_burned_edit_text)
        val durationEditText: EditText = view.findViewById(R.id.duration_edit_text)
        val addExerciseButton: Button = view.findViewById(R.id.add_exercise_button)
        val goBackButton: Button = view.findViewById(R.id.go_back)

        // Set click listeners for the buttons
        addExerciseButton.setOnClickListener {
            val exerciseName = exerciseNameEditText.text.toString()
            val calorieBurned = calorieBurnedEditText.text.toString()
            val duration = durationEditText.text.toString()

            listener?.onAddExerciseButtonClick(exerciseName, calorieBurned, duration)
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackExerciseButtonClick()
        }

        return view
    }
}
