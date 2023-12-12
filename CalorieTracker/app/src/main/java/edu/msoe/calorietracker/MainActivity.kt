package edu.msoe.calorietracker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    FrontFragment.OnFragmentInteractionListener,
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
    override fun onGoToAddFoodButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddFoodFragment.newInstance(), "addFood")
            .commit()
    }

    override fun onGoToAddExerciseButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddExerciseFragment.newInstance(), "addExercise")
            .commit()
    }

    // AddExerciseFragment
    override fun onGoToUniqueExerciseButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddUniqueExerciseFragment.newInstance(), "addUniqueExercise")
            .commit()
    }


    // AddFoodFragment
    override fun onGoToUniqueFoodButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddUniqueFoodFragment.newInstance(), "addUniqueFood")
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddFoodFragment.newInstance(), "goBackFood")
            .commit()
    }

    override fun onGoBackExerciseButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AddExerciseFragment.newInstance(), "goBackExercise")
            .commit()
    }

    override fun onGoBackHomeButtonClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FrontFragment.newInstance(), "goBackHome")
            .commit()
    }
}
