package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.math.pow

class BMIFragment : Fragment() {

    companion object {
        fun newInstance(): BMIFragment {
            return BMIFragment()
        }
    }

    interface OnFragmentInteractionListener {
        fun onGoBackHomeButtonClick()
        fun onBMICalculated(bmi: Double)
    }

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var bmiResultTextView: TextView

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
        val view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false)

        // Find the TextView by its ID
        bmiResultTextView = view.findViewById(R.id.bmi_result_text)

        val goBackButton: Button = view.findViewById(R.id.go_back)
        val calculateBMIButton: Button = view.findViewById(R.id.go_to_calorie_tracking_page)

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        calculateBMIButton.setOnClickListener {
            calculateBMI()
        }

        // Clear BMI result initially
        clearBMIResult()

        return view
    }

    private fun clearBMIResult() {
        bmiResultTextView.text = ""
    }

    private fun calculateBMI() {
        // Retrieve user input
        val weightEditText = view?.findViewById<EditText>(R.id.weight_edit_text)
        val heightFeetEditText = view?.findViewById<EditText>(R.id.height_feet_edit_text)
        val heightInchesEditText = view?.findViewById<EditText>(R.id.height_inches_edit_text)

        val weightStr = weightEditText?.text.toString()
        val heightFeetStr = heightFeetEditText?.text.toString()
        val heightInchesStr = heightInchesEditText?.text.toString()

        // Check if any of the input fields is empty
        if (weightStr.isEmpty() && heightFeetStr.isEmpty() && heightInchesStr.isEmpty()) {
            showToast("Please enter both weight and height.")
            clearBMIResult() // Clear BMI result
            return
        }

        val weight: Double = weightStr.toDoubleOrNull() ?: run {
            if (weightStr.isEmpty()) {
                showToast("Please enter weight.")
            } else {
                showToast("Please enter a valid weight.")
            }
            clearBMIResult() // Clear BMI result
            return
        }

        val heightFeet: Double = heightFeetStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid height (feet).")
            clearBMIResult() // Clear BMI result
            return
        }

        val heightInches: Double = heightInchesStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid height (inches).")
            clearBMIResult() // Clear BMI result
            return
        }

        // Check for negative values
        if (weight < 0 || heightFeet < 0 || heightInches < 0) {
            showToast("Please enter non-negative values for weight and height.")
            clearBMIResult() // Clear BMI result
            return
        }

        // Calculate BMI using the formula: weight (lb) / [height (in)]^2 x 703
        val heightInInches = (heightFeet * 12) + heightInches
        val bmi = (weight / (heightInInches.pow(2))) * 703

        // Check if BMI is negative
        if (bmi < 0) {
            showToast("Invalid BMI calculation. Please check your input values.")
            clearBMIResult() // Clear BMI result
            return
        }

        // Pass the calculated BMI to the listener
        listener?.onBMICalculated(bmi)
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun displayBMIResult(bmi: Double) {
        val roundedBMI = String.format("%.2f", bmi)
        bmiResultTextView.text = getString(R.string.bmi_result, "$roundedBMI (${interpretBMI(bmi)})")
    }

    private fun interpretBMI(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Healthy Weight"
            bmi in 25.0..29.9 -> "Overweight"
            bmi in 30.0..39.9 -> "Obese"
            bmi >= 40.0 -> "Severely Obese"
            else -> "Unknown"
        }
    }
}
