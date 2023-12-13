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

class AddFoodFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()

    companion object {
        fun newInstance(): AddFoodFragment {
            return AddFoodFragment()
        }
    }

    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onGoToUniqueFoodButtonClick()
        fun onGoBackHomeButtonClick()
        fun onGoFrontFragmentWithConsumedCalories(consumedCalories: Int)
    }

    private var listener: OnFragmentInteractionListener? = null

    // Mutex for synchronizing access to the foods list
    private val foodsMutex = Mutex()
    private var foods: List<Food> = emptyList()

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
        val view: View = inflater.inflate(R.layout.add_food_page, container, false)

        // Find buttons by their IDs
        val goToUniqueFoodButton: Button = view.findViewById(R.id.go_to_unique_food_button)
        val goBackButton: Button = view.findViewById(R.id.go_back)

        // Set click listeners for the buttons
        goToUniqueFoodButton.setOnClickListener {
            listener?.onGoToUniqueFoodButtonClick()
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        // Replace "add_food_button" with the actual ID of your button
        val addFoodButton: Button = view.findViewById(R.id.add_food)

        addFoodButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                // Use the mutex to ensure proper synchronization
                foodsMutex.withLock {
                    val spinner: Spinner = view.findViewById(R.id.common_foods_spinner)
                    val selectedPosition = spinner.selectedItemPosition
                    val selectedFood = if (selectedPosition != AdapterView.INVALID_POSITION) {
                        foods[selectedPosition]
                    } else {
                        // Handle the case where no food is selected
                        null
                    }

                    listener?.onGoFrontFragmentWithConsumedCalories(selectedFood?.calories ?: 0)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Food", "Start coroutine")

            viewModel.foods.collect { foodList ->
                // Use the mutex to ensure proper synchronization
                foodsMutex.withLock {
                    foods = foodList
                }

                val foodNames = ArrayList<String>()
                withContext(Dispatchers.Main) {
                    Log.d("Food", "Main context")
                    // Use the mutex to ensure proper synchronization
                    foodsMutex.withLock {
                        for (food: Food in foods) {
                            foodNames.add(food.name)
                        }
                    }

                    if (isAdded) {
                        val spinner: Spinner = view.findViewById(R.id.common_foods_spinner)
                        val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            foodNames
                        )
                        spinner.adapter = adapter

                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                // You can use 'food' as needed, e.g., food.calories
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // Do nothing
                            }
                        }
                    } else {
                        Log.d("Food", "Fragment not attached yet, delay")
                    }
                }
            }
        }
        return view
    }
}
