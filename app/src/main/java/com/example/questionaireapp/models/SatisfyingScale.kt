package com.example.questionaireapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SatisfyingScale(
    val questionId: String?,
    val questionType: String? = "4",
    val question: String?
) : Parcelable