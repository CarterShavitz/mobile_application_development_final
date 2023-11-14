package edu.msoe.calorietracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class FrontFragment : Fragment() {

    companion object {
        fun newInstance(): FrontFragment {
            return FrontFragment()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_front_details, container, false)
        return view
    }

    interface OnFrontFragmentInteractionListener {
        fun onFrontFragmentInteraction()
    }

}