package com.example.questionaireapp.ui.questionfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.questionaireapp.R
import com.example.questionaireapp.models.Answer
import com.example.questionaireapp.models.MultipleChoice
import com.example.questionaireapp.models.TrueFalseModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import java.util.*

class TrueFalseFragment : Fragment() {
    private lateinit var answersContainer: RadioGroup
    private lateinit var questionText: TextView
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_true_false, container, false)

        database = Firebase.database.reference
        val args = arguments
        val myBundle: TrueFalseModel? = args?.getParcelable("questionBundle")

        answersContainer = view.findViewById(R.id.rgAnswers)
        questionText = view.findViewById(R.id.tvQuestion)
        if (myBundle != null) {
            questionText.text = myBundle.question
        }

        return view
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
        database.child("answers").child("answer1").child(random).setValue(answer)
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

