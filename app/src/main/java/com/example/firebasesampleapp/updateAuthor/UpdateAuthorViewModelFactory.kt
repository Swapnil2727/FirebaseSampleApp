package com.example.firebasesampleapp.updateAuthor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesampleapp.data.Author


class UpdateAuthorViewModelFactory(private val author:Author) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateAuthorViewModel::class.java)) {
            return UpdateAuthorViewModel(author) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}