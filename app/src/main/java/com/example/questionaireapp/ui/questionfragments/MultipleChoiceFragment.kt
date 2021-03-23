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
import androidx.core.view.children
import com.example.questionaireapp.R
import com.example.questionaireapp.models.MultipleChoice
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_multiple_choice.*
import kotlinx.android.synthetic.main.fragment_multiple_choice.view.*
import java.lang.StringBuilder
import java.text.FieldPosition
import java.util.*

class MultipleChoiceFragment : Fragment() {

    private lateinit var answersContainer: RadioGroup
    private lateinit var questionText: TextView
    private lateinit var database: DatabaseReference
    private lateinit var chosenRadio: RadioButton
    private var ids: MutableList<Int> = mutableListOf(1,2,3,4,5,6,7,8,9,10)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get arguments
        database = Firebase.database.reference
        val args = arguments
        val myBundle: MultipleChoice? = args?.getParcelable("questionBundle")

        val view = inflater.inflate(R.layout.fragment_multiple_choice, container, false)
        answersContainer = view.findViewById<RadioGroup>(R.id.rgAnswers)
        questionText = view.findViewById(R.id.tvQuestion)

        //set answers
        if (myBundle != null) {
            questionText.text = myBundle.question
            val position = 0
            for (el in myBundle.choicesArray) {
                createAnswer(el, answersContainer,position)
            }
        }



        return view
    }

    private fun createAnswer(answer: String, parent: RadioGroup, position: Int) {
        //add the new buttons with their text to the radio group
        val radioButton = RadioButton(activity)
        radioButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton.text = answer

        //this will help when determining what is the button represented by the id on onDestroy
        radioButton.id = ids[position]
        parent.addView(radioButton)
    }

    override fun onDestroy() {
        super.onDestroy()
        val chosenId = answersContainer.checkedRadioButtonId

        Log.d("We try to push the answer with the ID",chosenId.toString() )
        val chosenAnswer =
            answersContainer.findViewById(answersContainer.checkedRadioButtonId) as RadioButton
        writeAnswer(questionText.text.toString(),chosenAnswer.text.toString())

        //remove all views
        answersContainer.removeAllViews()
        answersContainer.removeAllViewsInLayout()
    }

    private fun writeAnswer(questionText: String, answerText: String) {
        //create a random string id
        val random = getRandomString(10)
        Log.d("random generated is ", random.toString())
        //push value to firebase
        val answer = Answer(questionText, answerText)
        database.child("answers").child(random).setValue(answer)
    }

    private fun getRandomString(length: Int): String {
        val sb = StringBuilder(length)
        val alphabet = "asdfgfhjklqwertyuiopzxcvbnmASDFGHJKLZXCVBNMQWERTYUIOP1234567890"
        val rand = Random()

        for (i in 0 until sb.capacity()) {
            val index = rand.nextInt(alphabet.length)
            sb.append(alphabet[index])
        }
        return sb.toString()
    }



}
data class Answer(val questionText: String, val answerText: String)