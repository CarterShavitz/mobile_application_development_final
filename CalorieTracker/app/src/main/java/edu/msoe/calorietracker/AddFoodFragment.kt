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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        val view: View = inflater.inflate(R.layout.add_food_page, container, false)

        // Find buttons by their IDs
        val goToUniqueFoodButton: Button = view.findViewById(R.id.go_to_unique_food_button)
        val goBackButton: Button = view.findViewById(R.id.go_back)
        val goAddFood: Button = view.findViewById(R.id.add_food)

        // Set click listeners for the buttons
        goToUniqueFoodButton.setOnClickListener {
            listener?.onGoToUniqueFoodButtonClick()
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        goAddFood.setOnClickListener {
            listener?.onGoFrontFragment()
        }

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Food", "Start coroutine")

            var foods: List<Food> = emptyList()
            viewModel.foods.collect { foodList ->
                foods = foodList
                val foodNames = ArrayList<String>()
                withContext(Dispatchers.Main) {
                    Log.d("Food", "Main context")
                    for (food: Food in foods) {
                        foodNames.add(food.name)
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

                                val food = foods[position]
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
