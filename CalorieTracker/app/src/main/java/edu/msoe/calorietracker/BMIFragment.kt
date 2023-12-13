package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

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

    //Calorie Recommendations
    private lateinit var activitySpinner: Spinner // Declare activitySpinner
    private var weight: Double = 0.0
    private var heightFeet: Double = 0.0
    private var heightInches: Double = 0.0
    private lateinit var ageEditText: EditText
    private lateinit var maleRadioButton: RadioButton

    private val MAINTAIN_WEIGHT_PERCENTAGE = 100
    private val MILD_WEIGHT_LOSS_PERCENTAGE = 90
    private val WEIGHT_LOSS_PERCENTAGE = 80
    private val EXTREME_WEIGHT_LOSS_PERCENTAGE = 61

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

        // Initialize activitySpinner
        activitySpinner = view.findViewById(R.id.activity_spinner) // Replace with the actual ID of your Spinner
        ageEditText = view.findViewById(R.id.age_edit_text)
        maleRadioButton = view.findViewById(R.id.radio_male)

        val goBackButton: Button = view.findViewById(R.id.go_back)
        val calculateBMIButton: Button = view.findViewById(R.id.go_to_calorie_tracking_page)
        val calculateCalorieButton: Button = view.findViewById(R.id.calorie_recom)


        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        calculateBMIButton.setOnClickListener {
            calculateBMI()
        }

        calculateCalorieButton.setOnClickListener {
            showCalorieRecommendations()
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
            showToast("Please enter values for weight, height (feet), and height (inches).")
            clearBMIResult() // Clear BMI result
            return
        }

        val weight: Double = weightStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid weight.")
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
            showToast("Please enter non-negative values for weight, height (feet), and height (inches).")
            clearBMIResult() // Clear BMI result
            return
        }

        // Calculate BMI using the formula: weight (kg) / [height (in)]^2
        val heightInInches = (heightFeet * 12) + heightInches
        val bmi = weight / (heightInInches.pow(2)) * 703 // Multiply by 703 for the BMI calculation with height in inches

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
        val epsilon = 0.01 // Small value to handle floating-point imprecision

        return when {
            bmi < 18.5 - epsilon -> "Underweight"
            bmi in 18.5 - epsilon..24.9 + epsilon -> "Healthy Weight"
            bmi in 25.0 - epsilon..29.9 + epsilon -> "Overweight"
            bmi in 30.0 - epsilon..34.9 + epsilon -> "Obese (Class 1)"
            bmi in 35.0 - epsilon..39.9 + epsilon -> "Obese (Class 2)"
            bmi >= 40.0 - epsilon -> "Severely Obese"
            else -> "Unknown"
        }
    }

    private fun showCalorieRecommendations() {
        val weightEditText = view?.findViewById<EditText>(R.id.weight_edit_text)
        val heightFeetEditText = view?.findViewById<EditText>(R.id.height_feet_edit_text)
        val heightInchesEditText = view?.findViewById<EditText>(R.id.height_inches_edit_text)
        val ageEditText = view?.findViewById<EditText>(R.id.age_edit_text)

        val weightStr = weightEditText?.text.toString()
        val heightFeetStr = heightFeetEditText?.text.toString()
        val heightInchesStr = heightInchesEditText?.text.toString()
        val ageStr = ageEditText?.text.toString()


        // Check if any of the input fields is empty
        if (weightStr.isEmpty() || heightFeetStr.isEmpty() || heightInchesStr.isEmpty() || ageStr.isEmpty()) {
            showToast("Please enter all the required information.")
            return
        }

        // Convert input values to appropriate types
        val weightInput = weightStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid weight.")
            return
        }

        val heightFeetInput = heightFeetStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid height (feet).")
            return
        }

        val heightInchesInput = heightInchesStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid height (inches).")
            return
        }

        val ageInput = ageStr.toDoubleOrNull() ?: run {
            showToast("Please enter a valid age.")
            return
        }

        // Check for negative values
        if (weightInput < 0 || heightFeetInput < 0 || heightInchesInput < 0 || ageInput < 0) {
            showToast("Please enter non-negative values for weight, height, and age.")
            return
        }

        // Calculate BMR using Mifflin-St Jeor Equation and round to the nearest integer
        val bmr = calculateBMRMifflinStJeor(weightInput, heightFeetInput, heightInchesInput, ageInput, isMale())

        // Calculate daily calorie estimates based on specified rates and round to the nearest integer
        val maintainWeightCalories = calculateCalories(bmr, MAINTAIN_WEIGHT_PERCENTAGE)
        val mildWeightLossCalories = calculateCalories(bmr, MILD_WEIGHT_LOSS_PERCENTAGE)
        val weightLossCalories = calculateCalories(bmr, WEIGHT_LOSS_PERCENTAGE)
        val extremeWeightLossCalories = calculateCalories(bmr, EXTREME_WEIGHT_LOSS_PERCENTAGE)

        // Display the results
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Calorie Recommendations")

        val message = """
            Maintain weight: $maintainWeightCalories Calories/day
            Mild weight loss (0.5 lb/week): $mildWeightLossCalories Calories/day
            Weight loss (1 lb/week): $weightLossCalories Calories/day
            Extreme weight loss (2 lb/week): $extremeWeightLossCalories Calories/day
        """.trimIndent()

        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Respond to the button click if needed
        }

        val dialog = dialogBuilder.create()
        dialog.show()

    }

    private fun calculateCalories(bmr: Double, percentage: Int): Int {
        return (bmr * percentage / 100.0).roundToInt()
    }

    private fun calculateBMRMifflinStJeor(weight: Double, heightFeet: Double, heightInches: Double, age: Double, isMale: Boolean): Double {
        // Convert height to centimeters
        val heightInCm = ((heightFeet * 12) + heightInches) * 2.54

        // Use the Mifflin-St Jeor Equation
        return if (isMale) {
            // BMR for men: 10W + 6.25H - 5A + 5
            (10 * weight + 6.25 * heightInCm - 5 * age + 5)
        } else {
            // BMR for women: 10W + 6.25H - 5A - 161
            (10 * weight + 6.25 * heightInCm - 5 * age - 161)
        }
    }

    private fun isMale(): Boolean {
        // Assume that the user has correctly selected their gender
        return maleRadioButton.isChecked
    }
}
