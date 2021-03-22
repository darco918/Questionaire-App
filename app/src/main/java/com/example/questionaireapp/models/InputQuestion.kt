package com.example.questionaireapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InputQuestion(val questionId: Int,val questionType:Int = 2, val question: String ) :
    Parcelable {}