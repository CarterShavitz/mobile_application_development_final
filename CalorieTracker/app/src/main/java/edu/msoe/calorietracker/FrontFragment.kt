package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class FrontFragment : Fragment() {

    companion object {
        fun newInstance(): FrontFragment {
            return FrontFragment()
        }
    }
    // Interface to handle button clicks
    interface OnFragmentInteractionListener {
        fun onGoToAddExerciseButtonClick()
        fun onGoToAddFoodButtonClick()
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_front_details, container, false)
        val addFoodButton: Button = view.findViewById(R.id.add_food_btn)
        val addExerciseButton: Button = view.findViewById(R.id.add_exercise_btn)

        addFoodButton.setOnClickListener {
            listener?.onGoToAddFoodButtonClick()
        }

        addExerciseButton.setOnClickListener {
            listener?.onGoToAddExerciseButtonClick()
        }
        return view
    }

}