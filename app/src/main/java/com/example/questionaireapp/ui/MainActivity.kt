package com.example.questionaireapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.questionaireapp.R
import com.example.questionaireapp.models.MultipleChoice
import com.example.questionaireapp.ui.questionfragments.MultipleChoiceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //this is the question we are inflating
        val answers = listOf<String>("option 1 darco", "option 2", "option 3", "option 4")
        val question = MultipleChoice(1,1,"First question", answers)

        //Add the object to the fragment
        val currentQuestion = Bundle()
        currentQuestion.putParcelable("questionBundle", question)

        //inflate the fragment
        val fragment = MultipleChoiceFragment()
        fragment.arguments = currentQuestion
        supportFragmentManager.beginTransaction().add(R.id.rootLayout, fragment).commit()

        //when you click on next, the fragment will be replaces with a new one
        nextButton.setOnClickListener {
            val answers2 = listOf<String>("option 22", "option 22", "option 23", "option 24")
            val question2 = MultipleChoice(2,1,"First question", answers2)
            setQuestion(question2)

           // saveAnswer()
        }
    }

    private fun setQuestion(question: MultipleChoice){
        val fragment = MultipleChoiceFragment()
        val currentQuestion = Bundle()

        currentQuestion.putParcelable("questionBundle", question)

        fragment.arguments = currentQuestion

        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commit()

    }
}