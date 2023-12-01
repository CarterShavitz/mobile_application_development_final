package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        return view
    }

    private fun calculateBMI() {
        // Retrieve user input
        val weight: Double =
            view?.findViewById<EditText>(R.id.weight_edit_text)?.text.toString().toDoubleOrNull()
                ?: return // Return if weight is not a valid double
        val heightFeet: Double =
            view?.findViewById<EditText>(R.id.height_feet_edit_text)?.text.toString().toDoubleOrNull()
                ?: return // Return if heightFeet is not a valid double
        val heightInches: Double =
            view?.findViewById<EditText>(R.id.height_inches_edit_text)?.text.toString()
                .toDoubleOrNull()
                ?: return // Return if heightInches is not a valid double

        // Calculate BMI using the formula: weight (lb) / [height (in)]^2 x 703
        val heightInInches = (heightFeet * 12) + heightInches
        val bmi = (weight / (heightInInches.pow(2))) * 703

        // Pass the calculated BMI to the listener
        listener?.onBMICalculated(bmi)
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
