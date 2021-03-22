package com.example.questionaireapp.ui.questionfragments

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.questionaireapp.R
import com.example.questionaireapp.models.MultipleChoice
import kotlinx.android.synthetic.main.fragment_multiple_choice.*
import kotlinx.android.synthetic.main.fragment_multiple_choice.view.*

class MultipleChoiceFragment : Fragment() {

    private lateinit var answersContainer: RadioGroup
    private lateinit var questionText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get arguments
        val args = arguments
        val myBundle: MultipleChoice? = args?.getParcelable("questionBundle")

        val view = inflater.inflate(R.layout.fragment_multiple_choice, container, false)
        answersContainer = view.findViewById<RadioGroup>(R.id.rgAnswers)
        questionText = view.findViewById(R.id.tvQuestion)

        //set answers
        if (myBundle != null) {
            questionText.text = myBundle.question
            for (el in myBundle.choicesArray) {
                createAnswer(el, answersContainer)
            }
        }

        return view
    }

    private fun createAnswer(answer: String, parent: RadioGroup) {
        //add the new buttons with their text to the radio group
        val radioButton = RadioButton(activity)
        radioButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton.text = answer

        parent.addView(radioButton)
    }

    override fun onDestroy() {
        super.onDestroy()

        // HERE YOU SHOULD SAVE THE CHOSEN ANSWER TO FIREBASE OR JSON OR SHARED PREFERENCES

        val chosenAnswer =
            answersContainer.findViewById<RadioButton>(answersContainer.checkedRadioButtonId)

        Log.d("Question ", questionText.text.toString())
        Log.d("User chose ", chosenAnswer.text.toString())
        //DON'T FORGET TO ALSO ADD THE QUESTION TEXT
        answersContainer.removeAllViews()
        answersContainer.removeAllViewsInLayout()
    }

}