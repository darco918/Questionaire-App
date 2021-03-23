package com.example.questionaireapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InputQuestion(
    val questionId: String?,
    val questionType: String? = "2", val question: String?
) :
    Parcelable {}