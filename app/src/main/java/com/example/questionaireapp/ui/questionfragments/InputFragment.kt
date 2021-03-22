package com.example.questionaireapp.ui.questionfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.questionaireapp.R
import com.example.questionaireapp.models.InputQuestion
import com.example.questionaireapp.models.MultipleChoice
import kotlinx.android.synthetic.main.fragment_input.*

class InputFragment : Fragment() {
    private lateinit var inputAnswer: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        val myBundle: InputQuestion? = args?.getParcelable("questionBundle")

        val view = inflater.inflate(R.layout.fragment_input, container, false)
        inputAnswer = view.findViewById(R.id.editText)
        val tvQuestionInput = view.findViewById<TextView>(R.id.tvQuestionInput)
        if (myBundle != null) {
            tvQuestionInput.text = myBundle.question
            Log.d("Input answer is ", myBundle.question)
        }

        return view
    }


    override fun onDestroy() {
        super.onDestroy()

        // HERE YOU SHOULD SAVE THE CHOSEN ANSWER TO FIREBASE OR JSON OR SHARED PREFERENCES
        Log.d("Input answer is ", inputAnswer.text.toString())
    }


}