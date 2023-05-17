package com.example.hellokittyquiz
import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int,
                    val answer: Boolean,
                    var answered: String,
                    var answeredCorrectly: Boolean = false)

