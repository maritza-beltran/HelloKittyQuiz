package com.example.hellokittyquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

        var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val questionBank = listOf(
        Question(R.string.question1, true, "",false),
        Question(R.string.question2, false,"",false),
        Question(R.string.question3, true,"",false),
        Question(R.string.question4, false,"",false),
        Question(R.string.question5, false,"",false))


    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val nextQuestion: String?
        get() = if(currentIndex+ 1 < questionBank.size) {
            questionBank[currentIndex + 1].answered
        } else{
            null
        }

    val prevQuestion:String
        get() = questionBank[currentIndex - 1].answered

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrevious(){
        currentIndex = if(currentIndex == 0){
            (questionBank.size - 1)
        } else{
            (currentIndex - 1)
        }

    }
    fun setAnswerState(state:String){
        val correctAnswer = currentQuestionAnswer
        questionBank[currentIndex].answered = state
        questionBank[currentIndex].answeredCorrectly = (state == "True" && correctAnswer) || (state == "False" && !correctAnswer)
    }


}
