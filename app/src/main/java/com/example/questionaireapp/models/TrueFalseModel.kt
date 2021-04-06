package com.example.questionaireapp.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrueFalseModel(
    val questionId: String?,
    val questionType: String? = "3",
    val question: String?
) :
    Parcelable 