package com.example.questionaireapp.ui.questionfragments

import android.os.Bundle
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
import com.example.questionaireapp.models.Answer
import com.example.questionaireapp.models.MultipleChoice
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import java.util.*

class MultipleChoiceFragment : Fragment() {

    private lateinit var answersContainer: RadioGroup
    private lateinit var questionText: TextView
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get arguments
        database = Firebase.database.reference
        val args = arguments
        val myBundle: MultipleChoice? = args?.getParcelable("questionBundle")

        val view = inflater.inflate(R.layout.fragment_multiple_choice, container, false)
        answersContainer = view.findViewById(R.id.rgAnswers)
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

    private fun createAnswer(answer: String, parent: RadioGroup ) {
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
        val chosenId = answersContainer.checkedRadioButtonId

        Log.d("We try to push the answer with the ID",chosenId.toString() )
        for( i in 0..answersContainer.childCount){
            val view = answersContainer.getChildAt(i)
            if( view is RadioButton){
                if(view.isChecked)   writeAnswer(questionText.text.toString(), view.text.toString())
            }
        }
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
//this is to push an object once, instead of individual strings
