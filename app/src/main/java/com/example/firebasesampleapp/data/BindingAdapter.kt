package com.example.firebasesampleapp.data

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setAuthorName")
fun TextView.setAuthorName(author: Author)
{
    author?.let {
        text = author.name
    }
}