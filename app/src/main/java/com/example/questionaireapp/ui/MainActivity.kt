package com.example.questionaireapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.questionaireapp.R
import com.example.questionaireapp.models.InputQuestion
import com.example.questionaireapp.models.MultipleChoice
import com.example.questionaireapp.ui.questionfragments.InputFragment
import com.example.questionaireapp.ui.questionfragments.MultipleChoiceFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var questions: MutableList<Any?> = mutableListOf()
// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = Firebase.database.reference
//        writeNewQuestion(InputQuestion("23","2", "A doua intrebare"))
//        writeNewQuestion(InputQuestion("24","2", "A treia intrebare"))
//        writeNewQuestion(MultipleChoice("20","1", "A patra intrebare", listOf("sda","fasf","fasfsa")))

        // read data
        readDatabase()

        var position = 1

        questions.add(InputQuestion("dasfd", "2", "Test question LoCAL"))

        val question: InputQuestion = questions[0] as InputQuestion
        //Add the object to the fragment
        val currentQuestion = Bundle()
        currentQuestion.putParcelable("questionBundle", question)

        //inflate the fragment
        val fragment = InputFragment()
        fragment.arguments = currentQuestion
        supportFragmentManager.beginTransaction().add(R.id.rootLayout, fragment).commit()

        //when you click on next, the fragment will be replaces with a new one
        nextButton.setOnClickListener {
            if (position < questions.size) {
                if (questions[position] is MultipleChoice) {
                    setQuestionType1(questions[position] as MultipleChoice)
                    position += 1
                } else {
                    if (questions[position] is InputQuestion) {
                        setQuestionType2(questions[position] as InputQuestion)
                        position += 1

                    }
                }
            } else {
                finishedQuestions()
            }
        }

        // write to firebase snippet
    }

    private fun readDatabase() {
        database.child("form1").get().addOnSuccessListener {
            for (el in it.children) {

                val id = el.child("questionId").value.toString()
                val questionText = el.child("question").value.toString()
                val type = el.child("questionType").value.toString()
                Log.d("Type ", type)
                if (type == "2")

                    questions.add(InputQuestion(id, type, questionText))

                else {

                    val filtered = "[]"
                    var listChoices = el.child("choicesArray").value.toString()
                    listChoices = listChoices.filterNot { filtered.indexOf(it) > -1 }
                    listChoices = listChoices.replace("[","")
                    val choices = listChoices.split(",")
                    questions.add(MultipleChoice(id, type, questionText, choices))

                }
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }

    //optional function that will write questions to db
    private fun writeNewQuestion(question: Any) {
        //create a random string id
        val random = getRandomString(10)
        Log.d("random generated is ", random.toString())
        //push value to firebase
        database.child("form1").child(random).setValue(question)
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

    private fun setQuestionType1(question: MultipleChoice) {
        val fragment = MultipleChoiceFragment()
        val currentQuestion = Bundle()

        currentQuestion.putParcelable("questionBundle", question)

        fragment.arguments = currentQuestion

        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commit()

    }

    private fun setQuestionType2(question: InputQuestion) {
        val fragment = InputFragment()
        val currentQuestion = Bundle()
        currentQuestion.putParcelable("questionBundle", question)
        fragment.arguments = currentQuestion
        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commit()
    }

    private fun finishedQuestions() {
        val intent = Intent(this, EndActivity::class.java)
        startActivity(intent)
    }
}