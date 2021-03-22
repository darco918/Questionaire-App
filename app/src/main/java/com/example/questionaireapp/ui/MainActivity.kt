package com.example.questionaireapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.questionaireapp.R
import com.example.questionaireapp.models.InputQuestion
import com.example.questionaireapp.models.MultipleChoice
import com.example.questionaireapp.ui.questionfragments.InputFragment
import com.example.questionaireapp.ui.questionfragments.MultipleChoiceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //this is the question we are inflating



        val questions = listOf<Any>(MultipleChoice(1,1,"First question",  listOf<String>("option 1 darco", "option 2", "option 3", "option 4")),
            InputQuestion(5,2, "Introduceti raspunsul"),
            MultipleChoice(2,1,"Second question",  listOf<String>("option 21", "option 22", "option 23", "option 24")),
            MultipleChoice(3,1,"Third question",  listOf<String>("option 31", "option 32", "option 33", "option 34")),
            MultipleChoice(4,1,"Fourth question",  listOf<String>("option 41", "option 42", "option 43", "option 44"))
        )
        var position = 1

        val question:MultipleChoice = questions[0] as MultipleChoice
        //Add the object to the fragment
        val currentQuestion = Bundle()
        currentQuestion.putParcelable("questionBundle", question)

        //inflate the fragment
        val fragment = MultipleChoiceFragment()
        fragment.arguments = currentQuestion
        supportFragmentManager.beginTransaction().add(R.id.rootLayout, fragment).commit()

        //when you click on next, the fragment will be replaces with a new one
        nextButton.setOnClickListener {
            if(position < questions.size) {
                if (questions[position] is MultipleChoice) {
                    setQuestionType1(questions[position] as MultipleChoice)
                    position += 1
                }
                else{
                    if(questions[position] is InputQuestion){
                        setQuestionType2(questions[position] as InputQuestion)
                        position += 1

                    }
                }
            }
            else{
                finishedQuestions()
            }
        }
    }

    private fun setQuestionType1(question: MultipleChoice){
        val fragment = MultipleChoiceFragment()
        val currentQuestion = Bundle()

        currentQuestion.putParcelable("questionBundle", question)

        fragment.arguments = currentQuestion

        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commit()

    }

    private fun setQuestionType2(question: InputQuestion){
        val fragment = InputFragment()
        val currentQuestion = Bundle()
        currentQuestion.putParcelable("questionBundle", question)
        fragment.arguments = currentQuestion
        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commit()
    }

    private fun finishedQuestions(){
        val intent = Intent(this, EndActivity::class.java)
        startActivity(intent)
    }
}