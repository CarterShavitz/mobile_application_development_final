package edu.msoe.calorietracker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class FrontFragment : Fragment() {

    private val viewModel by activityViewModels<ViewModel>()


    companion object {
        fun newInstance(): FrontFragment {
            return FrontFragment()
        }
    }
    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onGoToAddExerciseButtonClick()
        fun onGoToAddFoodButtonClick()
        fun onGoToBMIPageButtonClick()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_front_details, container, false)
        val addFoodButton: Button = view.findViewById(R.id.add_food_btn)
        val addExerciseButton: Button = view.findViewById(R.id.add_exercise_btn)
        val addBMIButton: Button = view.findViewById(R.id.go_to_bmi_page)
        val datePicker: DatePicker = view.findViewById(R.id.date_picker)

        addFoodButton.setOnClickListener {
            listener?.onGoToAddFoodButtonClick()
        }

        addExerciseButton.setOnClickListener {
            listener?.onGoToAddExerciseButtonClick()
        }

        addBMIButton.setOnClickListener {
            listener?.onGoToBMIPageButtonClick()
        }

        fun DatePicker.getDate(): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, 1, 0, 0)
            return calendar.time
        }

        viewModel.setCurrentDate(datePicker.getDate())

        datePicker.setOnDateChangedListener {view, year, month, dayOfMonth ->
            Log.d("Front", "Date selected")
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
                set(Calendar.SECOND, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.HOUR, 1)
            }
            viewModel.setCurrentDate(selectedDate.time)
            Log.d("Front", selectedDate.time.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val calories = viewModel.getCalories(selectedDate.time)
                //Log.d("Front", calories.date.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    updateUIWithCalories(calories)
                }
            }
        }

        return view
    }

    // Method to update the UI with new calorie values
    private fun updateUIWithCalories(calories: Calories) {
        val caloriesConsumedTextView: TextView = view?.findViewById(R.id.calorie_value_text)!!
        val caloriesBurnedTextView: TextView = view?.findViewById(R.id.calories_burned_text)!!

        if (calories == null) {
            caloriesBurnedTextView.text = "0"
        } else {
            Log.d("Front", calories.date.time.toString())
            caloriesBurnedTextView.text = calories.caloriesBurned.toString()
        }
        if (calories == null) {
            caloriesConsumedTextView.text = "0"
        } else {
            Log.d("Front", calories.date.time.toString())
            caloriesConsumedTextView.text = calories.caloriesConsumed.toString()
        }
        // Update the UI with the new calorie values
    }

}