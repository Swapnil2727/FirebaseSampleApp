package com.example.firebasesample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentAddAuthorBinding
import com.example.firebasesampleapp.data.Author
import kotlinx.android.synthetic.main.fragment_add_author.*

/**
 * A simple [Fragment] subclass.
 */
class AddAuthorFragment : DialogFragment() {

    private lateinit var addAuthorViewModel: AddAuthorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Instance of ViewModel
        addAuthorViewModel = ViewModelProvider(this).get(AddAuthorViewModel::class.java)
        //get reference to binding and infalte fragment
        val binding: FragmentAddAuthorBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_author,container,false)

        binding.lifecycleOwner = this
        binding.addAuthorViewModel = addAuthorViewModel

        addAuthorViewModel.result.observe(viewLifecycleOwner, Observer {
        val message=
            if(it==null)
            {
                getString(R.string.success)
            }else{

                getString(R.string.error,it.message)
            }
            Log.i("AddAuthorFragment:","$message")
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
          //  dismiss()
        })
        //navigate back on success
        addAuthorViewModel.navigateToAuthorsFragment.observe(viewLifecycleOwner, Observer {
            if(it)
            {
                findNavController().navigate(R.id.action_addAuthorFragment_to_authorsFragment)
                addAuthorViewModel.navigateToAuthorFragmentComplete()
            }
        })

        return  binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addAuthor_button.setOnClickListener {
            val name = authorName_TextInputEditText.text.toString().trim()

            if(name.isEmpty())
            {
            text_inputLayout.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            //Add value into database
            val author = Author()
            author.name = name
            addAuthorViewModel.addAuthor(author)

        }
    }

}
