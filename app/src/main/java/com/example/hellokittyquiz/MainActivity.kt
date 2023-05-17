package com.example.hellokittyquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.hellokittyquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val quizViewModel: QuizViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        updateQuestion()

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            if(trueButton.isPressed){
                quizViewModel.setAnswerState("True")
                trueButton.isClickable = false
                falseButton.isClickable = false
            }


        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            if(falseButton.isPressed){
                quizViewModel.setAnswerState("False")
                trueButton.isClickable = false
                falseButton.isClickable = false
            }

        }
        binding.previousButton.setOnClickListener {
            if (quizViewModel.currentIndex==0){
                quizViewModel.currentIndex=(quizViewModel.currentIndex + quizViewModel.questionBank.size)
            }
            //quizViewModel.moveToPrev()
            if (quizViewModel.prevQuestion!=""){
                trueButton.isClickable=false
                falseButton.isClickable=false
                quizViewModel.moveToPrevious()
                updateQuestion()
            }else{
                trueButton.isClickable=true
                falseButton.isClickable=true
                quizViewModel.moveToPrevious()
                updateQuestion()
            }
        }

        binding.nextButton.setOnClickListener {
            //this part ensures that if the next question is not empty, you cannot click next button
            if (quizViewModel.nextQuestion!="") {
                trueButton.isClickable = false
                falseButton.isClickable = false
                quizViewModel.nextQuestion?.let { Log.d("aa", it) }
                showScore()
            }else{
                trueButton.isClickable=true
                falseButton.isClickable=true
                quizViewModel.moveToNext()
                updateQuestion()
            }
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        if (quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            showScore()
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_button
        }
        else{
            R.string.incorrect_button
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun showScore() {
        val numCorrect = quizViewModel.questionBank.filter { it.answeredCorrectly }.size
        val percentage = (numCorrect * 100) / quizViewModel.questionBank.size
        val stringScore = "You scored $percentage% on the quiz!"
        Toast.makeText(this, stringScore, Toast.LENGTH_SHORT).show()
    }
}