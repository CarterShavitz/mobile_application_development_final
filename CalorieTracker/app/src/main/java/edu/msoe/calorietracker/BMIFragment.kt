package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class BMIFragment : Fragment() {

    companion object {
        fun newInstance(): BMIFragment {
            return BMIFragment()
        }
    }

    interface OnFragmentInteractionListener {
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
        val view = inflater.inflate(R.layout.fragment_bmi_calculator, container, false)

        val goBackButton: Button = view.findViewById(R.id.go_back)

        goBackButton.setOnClickListener {
            listener?.onGoBackHomeButtonClick()
        }

        return view
    }
}
