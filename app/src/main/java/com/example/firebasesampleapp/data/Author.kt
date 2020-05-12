package com.example.firebasesampleapp.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Author(


    @get:Exclude
    var id: String? = null,

    var name: String? = null


) : Parcelable