package edu.msoe.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    FrontFragment.OnFrontFragmentInteractionListener,
    AddExerciseFragment.OnFragmentInteractionListener,
    AddFoodFragment.OnFragmentInteractionListener,
    AddUniqueExerciseFragment.OnFragmentInteractionListener,
    AddUniqueFoodFragment.OnFragmentInteractionListener {

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
    override fun onFrontFragmentInteraction() {
        // Handle interaction from FrontFragment if needed
    }

    // AddExerciseFragment
    override fun onGoToUniqueExerciseButtonClick() {
        // Handle interaction from AddExerciseFragment if needed
    }


    // AddFoodFragment
    override fun onGoToUniqueFoodButtonClick() {
        // Handle interaction from AddFoodFragment if needed
    }

    // AddUniqueExerciseFragment
    override fun onAddExerciseButtonClick(exerciseName: String, calorieBurned: String, duration: String) {
        // Handle interaction from AddUniqueExerciseFragment if needed
    }


    // AddUniqueFoodFragment
    override fun onAddFoodButtonClick(foodName: String, calorieCount: String, servingSize: String) {
        // Handle interaction from AddUniqueFoodFragment if needed
    }

    override fun onGoBackButtonClick() {
        // Handle interaction from AddUniqueFoodFragment if needed
    }
}
