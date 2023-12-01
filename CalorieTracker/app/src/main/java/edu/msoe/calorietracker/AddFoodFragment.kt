package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
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

        // Set click listeners for the buttons
        goToUniqueFoodButton.setOnClickListener {
            listener?.onGoToUniqueFoodButtonClick()
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val foods = viewModel.foods.toList()[0]

            withContext(Dispatchers.Main) {
                val spinner: Spinner = view.findViewById(R.id.common_foods_spinner)
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, foods)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        val selectedItem = foods[position]
                        

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Do nothing
                    }

                }
            }

        }

        return view
    }
}
