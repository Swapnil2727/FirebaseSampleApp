package com.example.firebasesample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.data.AuthorsListAdapter
import com.example.firebasesampleapp.databinding.FragmentAuthorsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class AuthorsFragment : Fragment() {

    private lateinit var viewModel: AddAuthorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AddAuthorViewModel::class.java)
        val binding: FragmentAuthorsBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_authors, container, false)

        binding.lifecycleOwner = this

        binding.authorsViewModel = viewModel

        val adapter = AuthorsListAdapter(AuthorsListAdapter.AuthorClickListner{
            viewModel.navigateToUpdateFragment(it)
        })

        //assign adapter
        binding.recylerView.adapter = adapter

        viewModel.fetchAuthors()


        viewModel.authors.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it) //add List to adapter

        })

        viewModel.navigateToUpdateFragment.observe(viewLifecycleOwner, Observer {

            it?.let {

                findNavController().navigate(AuthorsFragmentDirections.actionAuthorsFragmentToUpdateAuthorFragment(it))
                viewModel.onNavigationToUpdateComplete()
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val button: FloatingActionButton? = view?.findViewById(R.id.add_button)

        button?.setOnClickListener(View.OnClickListener {

            findNavController().navigate(R.id.addAuthorFragment)

        })


    }



}
