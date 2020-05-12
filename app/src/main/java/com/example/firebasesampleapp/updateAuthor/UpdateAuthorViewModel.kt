package com.example.firebasesampleapp.updateAuthor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasesampleapp.data.Author
import com.example.firebasesampleapp.data.NODE_AUTHORS
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class UpdateAuthorViewModel(author: Author): ViewModel() {


    private  val dbAuthor = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val _result =  MutableLiveData<Exception?>()

    val result : LiveData<Exception?> get() = _result

    //
    private val _authors = MutableLiveData<List<Author?>>()

    val authors : LiveData<List<Author?>> get() = _authors

    //
    private val _navigateToAuthorsFragment = MutableLiveData<Boolean>()

    val navigateToAuthorsFragment : LiveData<Boolean> get() = _navigateToAuthorsFragment

    fun updateAuthor(author:Author)
    {

        //Make id root of author
        dbAuthor.child(author.id!!).setValue(author).addOnCompleteListener {
            if(it.isSuccessful)
            {
                _result.value = null
                _navigateToAuthorsFragment.value=true
            }
            else
            {
                _result.value = it.exception
            }
        }
    }

    fun navigateToAuthorFragmentComplete()
    {
        _navigateToAuthorsFragment.value = false
    }
}