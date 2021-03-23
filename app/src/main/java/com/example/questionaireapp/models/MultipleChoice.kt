package com.example.questionaireapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MultipleChoice(val questionId: String,val questionType:String = "1", val question: String, val choicesArray: List<String>) :
    Parcelable {}