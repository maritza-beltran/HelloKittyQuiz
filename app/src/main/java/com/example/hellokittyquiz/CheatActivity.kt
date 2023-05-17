package com.example.hellokittyquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hellokittyquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.hellokittyquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.hellokittyquiz.answer_is_true"

class CheatActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener{
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }//end val
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }//end function

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }//return
        }//function
    }//companion object
}//class