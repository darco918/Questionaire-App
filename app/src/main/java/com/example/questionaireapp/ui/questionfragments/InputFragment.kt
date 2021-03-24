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
import com.example.questionaireapp.models.Answer
import com.example.questionaireapp.models.InputQuestion
import com.example.questionaireapp.models.MultipleChoice
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_input.*
import java.lang.StringBuilder
import java.util.*

class InputFragment : Fragment() {
    private lateinit var inputAnswer: EditText
    private lateinit var database: DatabaseReference
    private lateinit var questionText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = Firebase.database.reference
        val args = arguments
        val myBundle: InputQuestion? = args?.getParcelable("questionBundle")

        val view = inflater.inflate(R.layout.fragment_input, container, false)
        inputAnswer = view.findViewById(R.id.editText)
        questionText = view.findViewById(R.id.tvQuestionInput)
        if (myBundle != null) {
            questionText.text = myBundle.question
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        // HERE YOU SHOULD SAVE THE CHOSEN ANSWER TO FIREBASE OR JSON OR SHARED PREFERENCES
        // Log.d("Input answer is ", inputAnswer.text.toString())
        writeAnswer(questionText.text.toString(), inputAnswer.text.toString())
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