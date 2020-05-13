package com.example.firebasesample

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesampleapp.R
import com.example.firebasesampleapp.data.AuthorsListAdapter
import com.example.firebasesampleapp.databinding.FragmentAuthorsBinding
import com.example.firebasesampleapp.ui.SwipeToDeleteCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_authors.*

/**
 * A simple [Fragment] subclass.
 */
class AuthorsFragment : Fragment() {

    private lateinit var viewModel: AddAuthorViewModel
    private lateinit var adapter: AuthorsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AddAuthorViewModel::class.java)
        val binding: FragmentAuthorsBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_authors, container, false)

        binding.lifecycleOwner = this

        binding.authorsViewModel = viewModel

        adapter = AuthorsListAdapter(AuthorsListAdapter.AuthorClickListner{
            viewModel.navigateToUpdateFragment(it)
        })



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

        //Swipe to Delete
        val swipeHandler = object :SwipeToDeleteCallback(context)
        {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val author = adapter.currentList.get(position)
                viewModel.removeAuthor(author)

                Snackbar.make(view!!,"Author deleted",
                Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                    viewModel.addAuthor(author)
                }).setActionTextColor(Color.YELLOW).show()
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recylerView)



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
