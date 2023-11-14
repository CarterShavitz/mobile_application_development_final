package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText

class AddUniqueFoodFragment : Fragment() {

    companion object {
        fun newInstance(): AddUniqueFoodFragment {
            return AddUniqueFoodFragment()
        }
    }

    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onAddFoodButtonClick(foodName: String, calorieCount: String, servingSize: String)
        fun onGoBackButtonClick()
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
        val view: View = inflater.inflate(R.layout.add_unique_food, container, false)

        // Find views by their IDs
        val foodNameEditText: EditText = view.findViewById(R.id.food_name_edit_text)
        val calorieCountEditText: EditText = view.findViewById(R.id.calorie_count_edit_text)
        val servingSizeEditText: EditText = view.findViewById(R.id.serving_size_edit_text)
        val addFoodButton: Button = view.findViewById(R.id.add_food_button)
        val goBackButton: Button = view.findViewById(R.id.go_back)

        // Set click listeners for the buttons
        addFoodButton.setOnClickListener {
            val foodName = foodNameEditText.text.toString()
            val calorieCount = calorieCountEditText.text.toString()
            val servingSize = servingSizeEditText.text.toString()

            listener?.onAddFoodButtonClick(foodName, calorieCount, servingSize)
        }

        goBackButton.setOnClickListener {
            listener?.onGoBackButtonClick()
        }

        return view
    }
}
