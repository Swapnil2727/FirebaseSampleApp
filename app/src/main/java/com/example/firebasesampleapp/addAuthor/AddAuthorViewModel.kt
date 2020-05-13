package com.example.firebasesample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.firebasesampleapp.data.Author
import com.example.firebasesampleapp.data.NODE_AUTHORS
import java.lang.Exception

class AddAuthorViewModel: ViewModel() {

    private  val dbAuthor = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
    //
    private val _result =  MutableLiveData<Exception?>()

    val result : LiveData<Exception?> get() = _result

    //
    private val _authors = MutableLiveData<List<Author?>>()

    val authors : LiveData<List<Author?>> get() = _authors

    //
    private val _navigateToAuthorsFragment = MutableLiveData<Boolean>()

    val navigateToAuthorsFragment :LiveData<Boolean> get() = _navigateToAuthorsFragment


    //
    private val _navigateToUpdateFragment = MutableLiveData<Author?>()

    val navigateToUpdateFragment :LiveData<Author?> get() = _navigateToUpdateFragment



    fun addAuthor(author: Author)
    {

        author.id = dbAuthor.push().key

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

    fun fetchAuthors(){

        dbAuthor.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists())
                {
                    val authors = mutableListOf<Author>()
                    for(authorSnapShot in dataSnapshot.children){

                        val author = authorSnapShot.getValue(Author::class.java)
                        author?.id = authorSnapShot.key
                        author.let {
                            if (it != null) {
                                authors.add(it)
                            }
                        }

                    }
                    _authors.value = authors
                }

            }


        })

    }

    fun navigateToAuthorFragmentComplete()
    {
        _navigateToAuthorsFragment.value = false
    }

    fun navigateToUpdateFragment(author: Author)
    {
        _navigateToUpdateFragment.value = author
    }

    fun onNavigationToUpdateComplete()
    {
        _navigateToUpdateFragment.value = null
    }

    fun removeAuthor(author: Author)
    {

        dbAuthor.child(author.id!!).setValue(null).addOnCompleteListener {
            if(it.isSuccessful)
            {
                _result.value = null
               // _navigateToAuthorsFragment.value=true
            }
            else
            {
                _result.value = it.exception
            }

        }
    }

}