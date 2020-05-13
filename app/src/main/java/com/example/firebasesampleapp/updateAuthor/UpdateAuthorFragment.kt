package com.example.firebasesampleapp.updateAuthor

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.databinding.FragmentUpdateAuthorBinding
import kotlinx.android.synthetic.main.fragment_update_author.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateAuthorFragment : DialogFragment() {

    private lateinit var viewModel: UpdateAuthorViewModel
    private lateinit var viewModelFactory: UpdateAuthorViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments = UpdateAuthorFragmentArgs.fromBundle(requireArguments())
        viewModelFactory =UpdateAuthorViewModelFactory(arguments.author)
        viewModel = ViewModelProvider(this,viewModelFactory).get(UpdateAuthorViewModel::class.java)


        val binding: FragmentUpdateAuthorBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_update_author, container, false)

        binding.lifecycleOwner = this

        binding.updateAuthorViewModel = viewModel

        //set text from argument
        binding.updateAuthorNameTextInputEditText.setText(arguments.author.name)

        binding.updateAuthorButton.setOnClickListener {

            val name = updateAuthorName_TextInputEditText.text.toString().trim()

            if(name.isEmpty())
            {
                update_TextInputLayout.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            //update value into database
            arguments.author.name = name
            viewModel.updateAuthor(arguments.author)

            viewModel.navigateToAuthorsFragment.observe(viewLifecycleOwner, Observer {
                if(it){
                    findNavController().navigate(UpdateAuthorFragmentDirections.actionUpdateAuthorFragmentToAuthorsFragment())
                    viewModel.navigateToAuthorFragmentComplete()
                }

            })

        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    }




