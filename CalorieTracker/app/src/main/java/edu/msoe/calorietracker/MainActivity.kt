package edu.msoe.calorietracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    FrontFragment.OnFragmentInteractionListener,
    AddExerciseFragment.OnFragmentInteractionListener,
    AddFoodFragment.OnFragmentInteractionListener,
    AddUniqueExerciseFragment.OnFragmentInteractionListener,
    AddUniqueFoodFragment.OnFragmentInteractionListener,
    BMIFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, FrontFragment.newInstance(), "frontFragment")
                .commit()
        }
    }

    // Implement the interface methods for all fragments

    // FrontFragment
    override fun onGoToAddFoodButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddFoodFragment.newInstance(), "addFood")
            .addToBackStack(null)  // Add to back stack to handle popBackStack
            .commit()
    }

    override fun onGoToAddExerciseButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddExerciseFragment.newInstance(), "addExercise")
            .addToBackStack(null)  // Add to back stack to handle popBackStack
            .commit()
    }

    override fun onGoToBMIPageButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, BMIFragment.newInstance(), "bmiFragment")
            .addToBackStack(null)  // Add to back stack to handle popBackStack
            .commit()
    }

    // AddExerciseFragment
    override fun onGoToUniqueExerciseButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddUniqueExerciseFragment.newInstance(), "addUniqueExercise")
            .addToBackStack(null)  // Add to back stack to handle popBackStack
            .commit()
    }

    // AddFoodFragment
    override fun onGoToUniqueFoodButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddUniqueFoodFragment.newInstance(), "addUniqueFood")
            .addToBackStack(null)  // Add to back stack to handle popBackStack
            .commit()
    }

    // AddUniqueExerciseFragment
    override fun onAddExerciseButtonClick(exerciseName: String, calorieBurned: String, duration: String) {
        // Handle interaction from AddUniqueExerciseFragment if needed
    }

    // AddUniqueFoodFragment
    override fun onAddFoodButtonClick(foodName: String, calorieCount: String, servingSize: String) {
        // Handle interaction from AddUniqueFoodFragment if needed
    }

    override fun onGoBackFoodButtonClick() {
        supportFragmentManager.popBackStack()  // Go back to the previous fragment
    }

    override fun onGoBackExerciseButtonClick() {
        supportFragmentManager.popBackStack()  // Go back to the previous fragment
    }

    override fun onGoBackHomeButtonClick() {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // Go back to the FrontFragment and remove the back stack
    }

    override fun onBMICalculated(bmi: Double) {
        // Display the BMI result in the BMIFragment
        val bmiFragment = supportFragmentManager.findFragmentByTag("bmiFragment") as BMIFragment?
        bmiFragment?.displayBMIResult(bmi)
    }

    override fun onGoFrontFragment() {
        // Implement the onGoFrontFragment method
        val foodFragment = supportFragmentManager.findFragmentByTag("addFood") as AddFoodFragment?
        foodFragment?.let {
            // Perform any necessary actions before navigating back to the FrontFragment
            // For example, you can update the calories in the FrontFragment based on the selected food and exercise
            //val selectedFoodCalories = it.getSelectedFoodCalories()
            //val selectedExerciseCalories = it.getSelectedExerciseCalories()

            // Call the updateCalories method in FrontFragment
            //val frontFragment = supportFragmentManager.findFragmentByTag("frontFragment") as FrontFragment?
            //frontFragment?.updateUIWithCalories(selectedFoodCalories, selectedExerciseCalories)
        }

        // Navigate back to the FrontFragment
        supportFragmentManager.popBackStack("frontFragment", 0)
    }
}
